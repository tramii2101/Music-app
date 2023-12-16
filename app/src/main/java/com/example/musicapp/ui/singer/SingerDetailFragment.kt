package com.example.musicapp.ui.singer

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.adapters.SongItemAdapter
import com.example.musicapp.base.BaseFragment
import com.example.musicapp.databinding.FragmentSingerDetailBinding
import com.example.musicapp.ui.play.PlayActivity
import com.example.musicapp.utils.extensions.setLinearLayoutManager
import com.example.musicapp.viewmodel.SingerViewModel
import com.example.musicapp.viewmodel.SongViewModel


class SingerDetailFragment : BaseFragment<FragmentSingerDetailBinding>() {

    private val sharedPreferences by lazy {
        requireActivity().getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
    }

    private val songViewModel by lazy {
        SongViewModel()
    }

    private val songAdapter by lazy {
        SongItemAdapter()
    }

    private val singerViewModel by lazy {
        SingerViewModel()
    }
    lateinit var accessToken: String
    lateinit var singerId: String
    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSingerDetailBinding {
        return FragmentSingerDetailBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        accessToken = sharedPreferences.getString("accessToken", "").toString()
        singerId = sharedPreferences.getString("singerId", "").toString()
        binding.rcvListSong.setLinearLayoutManager(
            requireContext(),
            songAdapter,
            RecyclerView.HORIZONTAL
        )
    }

    override fun bindData() {
        singerViewModel.getSingerDetail(accessToken, singerId)
        songViewModel.getListSongBySinger(accessToken, singerId)

    }

    override fun handleEvent() {
        songAdapter.setOnClickItem { item, position ->
            if (item != null) {
                sharedPreferences.edit().putString("songId", item.id).apply()
            }
            sharedPreferences.edit().putInt("song_position", position).apply()
            val intent = Intent(requireContext(), PlayActivity::class.java)
            startActivity(intent)
        }
    }

}