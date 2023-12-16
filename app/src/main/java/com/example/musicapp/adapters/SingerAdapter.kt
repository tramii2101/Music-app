package com.example.musicapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.musicapp.base.BaseAdapterRCV
import com.example.musicapp.databinding.ItemSingerBinding
import com.example.musicapp.model.Singer

class SingerAdapter : BaseAdapterRCV<Singer, ItemSingerBinding>()  {
    override fun bindData(binding: ItemSingerBinding, item: Singer, position: Int) {
        binding.tvSinger.text = item.fullname
        Glide.with(binding.imgArtist.context).load(item.avatar).into(binding.imgArtist)
    }

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ItemSingerBinding {
        return ItemSingerBinding.inflate(inflater, parent, false)
    }
}