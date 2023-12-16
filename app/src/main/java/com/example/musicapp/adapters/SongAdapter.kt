package com.example.musicapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.musicapp.api.response.ResultSongAtHome
import com.example.musicapp.base.BaseAdapterRCV
import com.example.musicapp.databinding.ItemSongInHomeBinding

class SongAdapter : BaseAdapterRCV<ResultSongAtHome, ItemSongInHomeBinding>() {
    override fun bindData(binding: ItemSongInHomeBinding, item: ResultSongAtHome, position: Int) {
        Glide.with(binding.imgBackground.context).load(item.image).into(binding.imgBackground)
        binding.tvSong.text = item.title

    }

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ItemSongInHomeBinding {
        return ItemSongInHomeBinding.inflate(inflater, parent, false)
    }
}