package com.example.musicapp.ui

import android.content.Context
import android.content.SharedPreferences
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Handler
import android.view.LayoutInflater
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.musicapp.api.response.SongData
import com.example.musicapp.base.BaseActivity
import com.example.musicapp.databinding.ActivityPlayBinding
import com.example.musicapp.viewmodel.SearchViewModel
import com.example.musicapp.viewmodel.SongViewModel
import java.util.concurrent.TimeUnit

class PlayActivity : BaseActivity<ActivityPlayBinding>() {
    private val viewModel by lazy {
        ViewModelProvider(this)[SongViewModel::class.java]
    }
    private val sharePref: SharedPreferences by lazy {
        getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
    }

    private val songViewModel by lazy {
        ViewModelProvider(this)[SongViewModel::class.java]
    }

    private val searchViewModel by lazy {
        ViewModelProvider(this)[SearchViewModel::class.java]
    }

    val handler: Handler = Handler()
    override fun inflateViewBinding(layoutInflater: LayoutInflater): ActivityPlayBinding {
        return ActivityPlayBinding.inflate(layoutInflater)
    }

    var id = ""
    var accessToken = ""
    private var listSongId = mutableListOf<String>()
    private var previousFragment = ""
    var position = 0

//    private val mediaPlayer = MediaPlayerCommon.createMediaPlayer()

    var mediaPlayer = MediaPlayer()

    override fun initView() {
        position = sharePref.getInt("song_position", 0)

    }

    override fun bindData() {
        accessToken = sharePref.getString("accessToken", null).toString()
        previousFragment = sharePref.getString("previousFragment", null).toString()
        when (previousFragment) {
            "HomeFragment" -> listSongId = getSongAtHome("Bearer $accessToken")
            "SingerDetail" -> {
                val singerId = sharePref.getString("singerId", null)
                listSongId = getSongBySinger("Bearer $accessToken", singerId.toString())
            }

            "CategoryFragment" -> {
                val categoryId = sharePref.getString("categoryId", "")
                listSongId = getSongByCategory("Bearer $accessToken", categoryId.toString())
            }

        }

        id = sharePref.getString("songId", "").toString()
        if (id != "") {
            viewModel.getSong(accessToken, id)
        }
        viewModel.loading.observe(this) {
            if (!it && viewModel.song != null) {
                bindSongData(viewModel.song!!)
                fillData(viewModel.song!!.audio)
            }
        }
    }

    override fun handleEvent() {

        binding.seekBar.setOnSeekBarChangeListener(
            object : OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) {
                        mediaPlayer.seekTo(progress)
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    binding.currentTime.text = "0:00"
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    if (mediaPlayer.isPlaying) {
                        mediaPlayer.seekTo(binding.seekBar.progress)
                    }
                }

            }
        )

        mediaPlayer.setOnCompletionListener {
            binding.play.isChecked = false
            mediaPlayer.seekTo(0)
        }

        binding.play.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked)
                mediaPlayer.pause()
            else mediaPlayer.start()
        }

        binding.replay10.setOnClickListener {
            binding.seekBar.progress = (mediaPlayer.currentPosition).minus(10000)
            mediaPlayer.seekTo(binding.seekBar.progress)
        }

        binding.forward10.setOnClickListener {
            binding.seekBar.progress = (mediaPlayer.currentPosition).plus(10000)
            mediaPlayer.seekTo(binding.seekBar.progress)
        }

        binding.skipNext.setOnClickListener {
            if (position < listSongId.size - 1) {
                mediaPlayer.pause()
                mediaPlayer.stop()
                mediaPlayer.reset()
                mediaPlayer.release()
                mediaPlayer = MediaPlayer()
                position += 1
                songViewModel.getSong("Bearer $accessToken", listSongId[position])
                viewModel.loading.observe(this) {
                    if (!it && viewModel.song != null) {
                        bindSongData(viewModel.song!!)
                        fillData(viewModel.song!!.audio)            //set audio and start
//                        playMusic(mediaPlayer, viewModel.song!!.audio)
                    }
                }
            }
        }

        binding.skipPrevious.setOnClickListener {
            if (position > 0) {
                mediaPlayer.pause()
                mediaPlayer.stop()
                mediaPlayer.reset()
                mediaPlayer.release()
                position -= 1
                songViewModel.getSong("Bearer $accessToken", listSongId[position])
                viewModel.loading.observe(this) {
                    if (!it && viewModel.song != null) {
                        bindSongData(viewModel.song!!)
                        fillData(viewModel.song!!.audio)
//                        playMusic(mediaPlayer, viewModel.song!!.audio)
                    }
                }
            }
        }
    }


    private fun bindSongData(song: SongData) {
        Glide.with(applicationContext).load(song.image).into(binding.imgSong)
        binding.tvSinger.text = song.singer.fullname
        binding.tvSong.text = song.title
    }

    private fun fillData(link: String) {
        mediaPlayer = MediaPlayer()
        mediaPlayer.apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(link)
            prepare()
        }.setOnPreparedListener { media ->
            binding.seekBar.max = media.duration
            handler.post(object : Runnable {
                override fun run() {
                    val curDuration = mediaPlayer.currentPosition.toLong()
                    val time = String.format(
                        "%02d:%02d ",
                        TimeUnit.MILLISECONDS.toMinutes(curDuration),
                        TimeUnit.MILLISECONDS.toSeconds(curDuration) - TimeUnit.MINUTES.toSeconds(
                            TimeUnit.MILLISECONDS.toMinutes(
                                curDuration
                            )
                        )
                    )
                    binding.totalTime.text = timerConversion(mediaPlayer.duration)
                    binding.currentTime.text = time
                    binding.seekBar.progress = curDuration.toInt()
                    handler.postDelayed(this, 1000)
                }
            })
            mediaPlayer.start()
        }
    }
    fun timerConversion(value: Int): String {
        var audioTime = ""
        val hrs = value / 3600000
        val mns = value / 60000 % 60000
        val scs = value % 60000 / 1000
        audioTime = if (hrs > 0) {
            String.format("%02d:%02d:%02d", hrs, mns, scs)
        } else {
            String.format("%02d:%02d", mns, scs)
        }
        return audioTime
    }

    private fun getSongAtHome(accessToken: String): MutableList<String> {
        val listId = mutableListOf<String>()
        songViewModel.getListSong(accessToken)
        songViewModel.loading.observe(this) {
            if (!it && songViewModel.listSongAtHome.isNotEmpty()) {
                for (i in 0..<songViewModel.listSongAtHome.size) {
                    listId.add(songViewModel.listSongAtHome[i].id.toString())
                }
            }
        }
        return listId
    }

    private fun getSongBySinger(accessToken: String, singerId: String): MutableList<String> {
        val listId = mutableListOf<String>()
        songViewModel.getListSongBySinger(accessToken, singerId)
        songViewModel.loading.observe(this) {
            if (!it) {
                for (i in songViewModel.listSongBySinger) {
                    listId.add(i.id.toString())
                }
            }
        }
        return listId
    }

    private fun getSongByCategory(accessToken: String, categoryId: String): MutableList<String> {
        val listId = mutableListOf<String>()
        songViewModel.getListSongByCategory(accessToken, categoryId)
        songViewModel.loading.observe(this) {
            if (!it) {
                for (i in songViewModel.listSongCategory) {
                    listId.add(i.id.toString())
                }
            }
        }
        return listId
    }





    private fun playMusic(mediaPlayer: MediaPlayer, audio: String) {
        mediaPlayer.setDataSource(audio)
        mediaPlayer.prepare()
        mediaPlayer.setOnPreparedListener {
            binding.seekBar.max = mediaPlayer.duration
            handler.post(object : Runnable {
                override fun run() {
                    val curDuration = mediaPlayer.currentPosition.toLong()
                    if (curDuration != null) {
                        val time = String.format(
                            "%02d:%02d ",
                            TimeUnit.MILLISECONDS.toMinutes(curDuration),
                            TimeUnit.MILLISECONDS.toSeconds(curDuration) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(
                                    curDuration
                                )
                            )
                        )
                        binding.totalTime.text = timerConversion(mediaPlayer.duration)
                        binding.currentTime.text = time
                        binding.seekBar.progress = curDuration.toInt()
                    }

                    handler.postDelayed(this, 1000)
                }
            })
            mediaPlayer.start()
        }
    }

}