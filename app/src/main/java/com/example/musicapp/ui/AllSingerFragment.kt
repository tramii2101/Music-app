package com.example.musicapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.R
import com.example.musicapp.adapters.SingerResultAdapter
import com.example.musicapp.base.BaseFragment
import com.example.musicapp.databinding.FragmentAllSingerBinding
import com.example.musicapp.utils.extensions.setLinearLayoutManager
import com.example.musicapp.viewmodel.SingerViewModel

class AllSingerFragment : BaseFragment<FragmentAllSingerBinding>() {

    private val sharedPreferences by lazy {
        requireActivity().getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
    }

    private val singerAdapter by lazy {
        SingerResultAdapter()
    }

    private val singerViewModel by lazy {
        ViewModelProvider(this)[SingerViewModel::class.java]
    }

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAllSingerBinding {
        return FragmentAllSingerBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        binding.listSingers.setLinearLayoutManager(
            requireContext(), singerAdapter, RecyclerView.VERTICAL
        )
    }

    override fun bindData() {
        val accessToken = sharedPreferences.getString("accessToken", "")
        singerViewModel.getListSinger("Bearer $accessToken")
        singerViewModel.loading.observe(this) {
            if (!it && singerViewModel.listSinger.isNotEmpty()) {
                singerAdapter.setDataList(singerViewModel.listSinger)
            }
        }
    }

    override fun handleEvent() {
        singerAdapter.setOnClickItem { item, position ->
            val singer = singerViewModel.listSinger[position]
            if (item != null) {
                sharedPreferences.edit().putString("singerId", singer.id.toString()).apply()
                findNavController().navigate(R.id.action_allSingerFragment_to_singerDetailFragment)
            }
        }
        binding.imgBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

    }

}