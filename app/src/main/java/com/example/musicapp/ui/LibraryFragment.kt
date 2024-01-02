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
//            findNavController().navigate(R.id.action_libraryFragment_to_allCategoryFragment)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, AllCategoryFragment()).addToBackStack(null)
                .commit()
        }

        binding.singer.setOnClickListener {
//            findNavController().navigate(R.id.action_libraryFragment_to_allSingerFragment)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, AllSingerFragment()).addToBackStack(null)
                .commit()
        }

        binding.song.setOnClickListener {
//            findNavController().navigate(R.id.action_libraryFragment_to_allSongFragment)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, AllSongFragment()).addToBackStack(null)
                .commit()
        }
    }

}