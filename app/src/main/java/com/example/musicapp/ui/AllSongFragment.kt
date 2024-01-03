package com.example.musicapp.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.adapters.SongAllAdapter
import com.example.musicapp.base.BaseFragment
import com.example.musicapp.databinding.FragmentAllSongBinding
import com.example.musicapp.utils.common.Screen
import com.example.musicapp.utils.extensions.setLinearLayoutManager
import com.example.musicapp.viewmodel.SongViewModel

class AllSongFragment : BaseFragment<FragmentAllSongBinding>() {
    private val songAdapter by lazy {
        SongAllAdapter()
    }

    private val sharedPreferences by lazy {
        requireActivity().getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
    }

    private val songViewModel by lazy {
        ViewModelProvider(this)[SongViewModel::class.java]
    }

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAllSongBinding {
        return FragmentAllSongBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        binding.recyclerListSong.setLinearLayoutManager(
            requireContext(),
            songAdapter,
            RecyclerView.VERTICAL
        )
    }

    override fun bindData() {
        val accessToken = sharedPreferences.getString("accessToken", "")
        songViewModel.getListSong("Bearer $accessToken")
        songViewModel.loading.observe(this) {
            if (!it && songViewModel.listSongAtHome.isNotEmpty()) {
                songAdapter.setDataList(songViewModel.listSongAtHome)
            }
        }
    }

    override fun handleEvent() {
        songAdapter.setOnClickItem { item, position ->
            sharedPreferences.edit().putString("previous_screen", Screen.LOGIN.toString()).apply()
            if (item != null) {
                sharedPreferences.edit()
                    .putString("songId", item.id).apply()
            }
            sharedPreferences.edit().putInt("song_position", position).apply()
            val intent = Intent(requireContext(), PlayActivity::class.java)
            startActivity(intent)
        }

        binding.imgBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

}