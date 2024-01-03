package com.example.musicapp.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.R
import com.example.musicapp.adapters.CategoryAdapter
import com.example.musicapp.adapters.SingerAdapter
import com.example.musicapp.adapters.SongAdapter
import com.example.musicapp.base.BaseFragment
import com.example.musicapp.databinding.FragmentHomeBinding
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
        activity?.let { ViewModelProvider(it) }!![SongViewModel::class.java]
    }

    private val singerViewModel by lazy {
        ViewModelProvider(this)[SingerViewModel::class.java]
    }

    private val categoryViewModel by lazy {
        ViewModelProvider(this)[CategoryViewModel::class.java]
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
                sharedPreferences.edit()
                    .putString("songId", item.id).apply()
            }
            sharedPreferences.edit().putInt("song_position", position).apply()
            sharedPreferences.edit().putString("previousFragment", "HomeFragment").apply()
            val intent = Intent(requireContext(), PlayActivity::class.java)
            startActivity(intent)
        }

        categoryAdapter.setOnClickItem { item, _ ->
            if (item != null) {
                sharedPreferences.edit().putString("categoryId", item.id).apply()
                sharedPreferences.edit().putString("categoryName", item.name).apply()
                findNavController().navigate(R.id.action_homeFragment_to_categoryFragment)
            }
        }

        singerAdapter.setOnClickItem { item, position ->
            val singer = singerViewModel.listSinger[position]
            if (item != null) {
                sharedPreferences.edit().putString("singerId", singer.id.toString()).apply()
                findNavController().navigate(R.id.action_homeFragment_to_singerDetailFragment)
            }
        }
    }

}