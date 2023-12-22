package com.example.musicapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.musicapp.api.response.MusicData
import com.example.musicapp.base.BaseAdapterRCV
import com.example.musicapp.databinding.ItemSongBinding

class SongItemAdapter : BaseAdapterRCV<MusicData, ItemSongBinding>() {
    override fun bindData(binding: ItemSongBinding, item: MusicData, position: Int) {
        Glide.with(binding.imageSong.context).load(item.image).into(binding.imageSong)
        binding.tvSongName.text = item.title
    }

    override fun inflateViewBinding(inflater: LayoutInflater, parent: ViewGroup): ItemSongBinding {
        return ItemSongBinding.inflate(inflater, parent, false)
    }
}