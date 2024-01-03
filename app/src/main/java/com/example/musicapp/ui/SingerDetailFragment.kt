package com.example.musicapp.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicapp.adapters.SongItemAdapter
import com.example.musicapp.base.BaseFragment
import com.example.musicapp.databinding.FragmentSingerDetailBinding
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
    var singerId: String = ""
    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSingerDetailBinding {
        return FragmentSingerDetailBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        accessToken = sharedPreferences.getString("accessToken", null).toString()
        singerId = sharedPreferences.getString("singerId", "").toString()
        binding.rcvListSong.setLinearLayoutManager(
            requireContext(),
            songAdapter,
            RecyclerView.VERTICAL
        )
    }

    override fun bindData() {
        songViewModel.getListSongBySinger("Bearer $accessToken", singerId)
        singerViewModel.getSingerDetail("Bearer $accessToken", singerId)
        singerViewModel.loading.observe(this) {
            if (!it) {
                Glide.with(binding.imgAvatar.context).load(singerViewModel.singer.avatar)
                    .into(binding.imgAvatar)
                binding.tvFullname.text = singerViewModel.singer.fullname
                binding.tvDes.text = singerViewModel.singer.description
            }
        }

        songViewModel.loading.observe(this) {
            if (!it) {
                songAdapter.setDataList(songViewModel.listSongBySinger)
            }
        }
    }

    override fun handleEvent() {
        songAdapter.setOnClickItem { item, position ->
            if (item != null) {
                sharedPreferences.edit().putString("songId", item.id).apply()
            }
            sharedPreferences.edit().putInt("song_position", position).apply()
            sharedPreferences.edit().putString("previousFragment", "SingerDetail").apply()
            val intent = Intent(requireContext(), PlayActivity::class.java)
            startActivity(intent)
        }

        binding.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

}