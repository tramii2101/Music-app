package com.example.musicapp.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.R
import com.example.musicapp.adapters.CategoryAdapter
import com.example.musicapp.adapters.SingerResultAdapter
import com.example.musicapp.adapters.SongResultAdapter
import com.example.musicapp.base.BaseFragment
import com.example.musicapp.databinding.FragmentExploreBinding
import com.example.musicapp.utils.extensions.setGridLayoutManager
import com.example.musicapp.utils.extensions.setLinearLayoutManager
import com.example.musicapp.viewmodel.SearchViewModel


class ExploreFragment : BaseFragment<FragmentExploreBinding>() {

    private var keyword = ""

    private val sharedPreferences by lazy {
        requireActivity().getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[SearchViewModel::class.java]
    }

    private val singerAdapter by lazy {
        SingerResultAdapter()
    }

    private val songAdapter by lazy {
        SongResultAdapter()
    }

    private val categoryAdapter by lazy {
        CategoryAdapter()
    }

    override fun inflateLayout(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentExploreBinding {
        return FragmentExploreBinding.inflate(inflater, container, false)
    }

    override fun initView() {

        binding.listResultsSingers.setLinearLayoutManager(
            requireContext(), singerAdapter, RecyclerView.VERTICAL
        )
        binding.listResultsSongs.setLinearLayoutManager(
            requireContext(), songAdapter, RecyclerView.VERTICAL
        )
        binding.listResultsCategory.setGridLayoutManager(
            requireContext(), categoryAdapter, RecyclerView.VERTICAL, 2
        )
    }

    override fun bindData() {

    }

    override fun handleEvent() {
        val accessToken = sharedPreferences.getString("accessToken", null)
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    keyword = query
                    viewModel.search("Bearer $accessToken", keyword)
                    viewModel.loading.observe(this@ExploreFragment) {
                        if (!it) {
                            if (viewModel.listSinger.size != 0) {
                                binding.rsSinger.visibility = View.VISIBLE
                                singerAdapter.setDataList(viewModel.listSinger)
                            }
                            if (viewModel.listSong.size != 0) {
                                binding.rsSong.visibility = View.VISIBLE
                                songAdapter.setDataList(viewModel.listSong)
                            }
                            if (viewModel.listSinger.size != 0) {
                                binding.rsCategory.visibility = View.VISIBLE
                                categoryAdapter.setDataList(viewModel.listCategory)
                            }
                        }
                    }
                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) {
                    clearData()
                }
                return false
            }

        })


        singerAdapter.setOnClickItem { item, _ ->
            if (item != null) {
                sharedPreferences.edit().putString("singerId", item.id).apply()
                findNavController().navigate(R.id.action_exploreFragment_to_singerDetailFragment)
            }
        }

        categoryAdapter.setOnClickItem { item, _ ->
            if (item != null) {
                sharedPreferences.edit().putString("categoryId", item.id).apply()
                findNavController().navigate(R.id.action_exploreFragment_to_categoryFragment)
            }
        }

        songAdapter.setOnClickItem { item, position ->
            if (item != null) {
                sharedPreferences.edit().putString("songId", item.id).apply()
                sharedPreferences.edit().putInt("songPosition", position).apply()
                val intent = Intent(this.context, PlayActivity::class.java)
                startActivity(intent)
            }
        }

        binding.radioFilter.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.radio_all) {
                binding.rsSinger.visibility = View.VISIBLE
                binding.rsSong.visibility = View.VISIBLE
                binding.rsCategory.visibility = View.VISIBLE
            }

            if (checkedId == R.id.radio_singer) {
                binding.rsSinger.visibility = View.VISIBLE
                binding.rsSong.visibility = View.GONE
                binding.rsCategory.visibility = View.GONE
            }

            if (checkedId == R.id.radio_song) {
                binding.rsSinger.visibility = View.GONE
                binding.rsSong.visibility = View.VISIBLE
                binding.rsCategory.visibility = View.GONE
            }

            if (checkedId == R.id.radio_category) {
                binding.rsSinger.visibility = View.GONE
                binding.rsCategory.visibility = View.VISIBLE
                binding.rsSong.visibility = View.GONE
            }
        }

    }

    fun clearData() {
        binding.result.visibility = View.GONE
        songAdapter.setDataList(mutableListOf())
        singerAdapter.setDataList(mutableListOf())
        categoryAdapter.setDataList(mutableListOf())
    }

}