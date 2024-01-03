package com.example.musicapp.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.adapters.SongItemAdapter
import com.example.musicapp.base.BaseFragment
import com.example.musicapp.databinding.FragmentCategoryBinding
import com.example.musicapp.utils.extensions.setLinearLayoutManager
import com.example.musicapp.viewmodel.SongViewModel


class CategoryFragment : BaseFragment<FragmentCategoryBinding>() {
    private val sharedPreferences by lazy {
        requireActivity().getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
    }

    private val songAdapter by lazy {
        SongItemAdapter()
    }

    private val songViewModel by lazy {
        SongViewModel()
    }


    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCategoryBinding {
        return FragmentCategoryBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        val categoryName = sharedPreferences.getString("categoryName", null)
        binding.tvCategory.text = categoryName
        binding.rcvListSong.setLinearLayoutManager(
            requireContext(),
            songAdapter,
            RecyclerView.VERTICAL
        )
    }

    override fun bindData() {
        val accessToken = sharedPreferences.getString("accessToken", "")
        val categoryId = sharedPreferences.getString("categoryId", "")
        if (accessToken != null) {
            if (categoryId != null) {
                songViewModel.getListSongByCategory("Bearer $accessToken", categoryId)
            }
        }
        songViewModel.loading.observe(this) {
            if (!it) {
                songAdapter.setDataList(songViewModel.listSongCategory)
            }
        }
    }

    override fun handleEvent() {
        songAdapter.setOnClickItem { item, position ->
            if (item != null) {
                sharedPreferences.edit().putString("songId", item.id).apply()
            }
            sharedPreferences.edit().putInt("songPosition", position).apply()
            sharedPreferences.edit().putString("previousFragment", "CategoryFragment").apply()
            val intent = Intent(requireContext(), PlayActivity::class.java)
            startActivity(intent)
        }

        binding.imgBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

}