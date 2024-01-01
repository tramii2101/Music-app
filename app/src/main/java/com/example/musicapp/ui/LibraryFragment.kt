package com.example.musicapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.musicapp.R
import com.example.musicapp.base.BaseFragment
import com.example.musicapp.databinding.FragmentLibraryBinding


class LibraryFragment : BaseFragment<FragmentLibraryBinding>() {
    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLibraryBinding {
        return FragmentLibraryBinding.inflate(inflater, container, false)
    }

    override fun initView() {

    }

    override fun bindData() {

    }

    override fun handleEvent() {
        binding.category.setOnClickListener {
            findNavController().navigate(R.id.action_libraryFragment_to_allCategoryFragment)
        }

        binding.singer.setOnClickListener {
            findNavController().navigate(R.id.action_libraryFragment_to_allSingerFragment)
        }

        binding.song.setOnClickListener {
            findNavController().navigate(R.id.action_libraryFragment_to_allSongFragment)
        }
    }

}