package com.example.musicapp.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.view.LayoutInflater
import android.widget.SeekBar
import android.widget.SeekBar.*
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.musicapp.api.response.SongData
import com.example.musicapp.base.BaseActivity
import com.example.musicapp.databinding.ActivityPlayBinding
import com.example.musicapp.utils.common.MediaPlayerCommon
import com.example.musicapp.viewmodel.SongViewModel
import java.util.concurrent.TimeUnit

class PlayActivity : BaseActivity<ActivityPlayBinding>() {
    private val viewModel by lazy {
        ViewModelProvider(this)[SongViewModel::class.java]
    }
    private val sharePref: SharedPreferences by lazy {
        getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
    }


    val handler: Handler = Handler()
    override fun inflateViewBinding(layoutInflater: LayoutInflater): ActivityPlayBinding {
        return ActivityPlayBinding.inflate(layoutInflater)
    }

    var id = ""

    private val mediaPlayer = MediaPlayerCommon.createMediaPlayer()

    override fun initView() {

    }

    override fun bindData() {
        val accessToken = sharePref.getString("accessToken", "")
        id = sharePref.getString("songId", "").toString()
        if (accessToken != null && id != "") {
            viewModel.getSong(accessToken, id)
        }
        viewModel.loading.observe(this) {
            if (!it && viewModel.song != null) {
                bindSongData(viewModel.song!!)
                mediaPlayer.setDataSource(viewModel.song!!.audio)
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
    }

    override fun handleEvent() {
        mediaPlayer.setOnPreparedListener {
            binding.seekBar.max = it.duration
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

        binding.seekBar.setOnSeekBarChangeListener(
            object : OnSeekBarChangeListener{
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
    }

    private fun bindSongData(song: SongData) {
        Glide.with(applicationContext).load(song.image).into(binding.imgSong)
        binding.tvSinger.text = song.singer.fullname
        binding.tvSong.text = song.title
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

}