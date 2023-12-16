package com.example.musicapp.ui.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.adapters.CategoryAdapter
import com.example.musicapp.adapters.SingerAdapter
import com.example.musicapp.adapters.SongAdapter
import com.example.musicapp.base.BaseFragment
import com.example.musicapp.databinding.FragmentHomeBinding
import com.example.musicapp.ui.play.PlayActivity
import com.example.musicapp.utils.common.Screen
import com.example.musicapp.utils.extensions.setGridLayoutManager
import com.example.musicapp.utils.extensions.setLinearLayoutManager
import com.example.musicapp.viewmodel.CategoryViewModel
import com.example.musicapp.viewmodel.SingerViewModel
import com.example.musicapp.viewmodel.SongViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val sharedPreferences by lazy {
        requireActivity().getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
    }

    private val singerAdapter by lazy {
        SingerAdapter()
    }

    private val songAdapter by lazy {
        SongAdapter()
    }

    private val categoryAdapter by lazy {
        CategoryAdapter()
    }

    private val songViewModel by lazy {
        SongViewModel()
    }

    private val singerViewModel by lazy {
        SingerViewModel()
    }

    private val categoryViewModel by lazy {
        CategoryViewModel()
    }

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        binding.recyclerListSong.setLinearLayoutManager(
            requireContext(),
            songAdapter,
            RecyclerView.HORIZONTAL
        )

        binding.recyclerSinger.setLinearLayoutManager(
            requireContext(),
            singerAdapter,
            RecyclerView.HORIZONTAL
        )

        binding.recyclerCategory.setGridLayoutManager(
            requireContext(),
            categoryAdapter,
            RecyclerView.VERTICAL,
            2
        )
    }

    override fun bindData() {
        val accessToken = sharedPreferences.getString("accessToken", "")

        songViewModel.getListSong("Bearer $accessToken")
        categoryViewModel.getListCategories("Bearer $accessToken")
        singerViewModel.getListSinger("Bearer $accessToken")

        singerViewModel.loading.observe(this) {
            if (!it && singerViewModel.listSinger.isNotEmpty()) {
                singerAdapter.setDataList(singerViewModel.listSinger)
            }
        }

        songViewModel.loading.observe(this) {
            if (!it && songViewModel.listSongAtHome.isNotEmpty()) {
                songAdapter.setDataList(songViewModel.listSongAtHome)
            }
        }

        categoryViewModel.loading.observe(this) {
            if (!it && categoryViewModel.categories.isNotEmpty()) {
                categoryAdapter.setDataList(categoryViewModel.categories)
            }
        }
    }

    override fun handleEvent() {
        songAdapter.setOnClickItem { item, position ->
            sharedPreferences.edit().putString("previous_screen", Screen.LOGIN.toString()).apply()
            if (item != null) {
                sharedPreferences.edit().putString("songId", item.id).apply()
            }
            sharedPreferences.edit().putInt("song_position", position).apply()
            val intent = Intent(requireContext(), PlayActivity::class.java)
            startActivity(intent)
        }

        categoryAdapter.setOnClickItem { item, _ ->
            if (item != null) {
                sharedPreferences.edit().putString("categoryId", item.id).apply()
            }
        }

        singerAdapter.setOnClickItem { item, _ ->
            if (item != null) {
                sharedPreferences.edit().putString("singerId", item.id).apply()
            }
        }
    }

}