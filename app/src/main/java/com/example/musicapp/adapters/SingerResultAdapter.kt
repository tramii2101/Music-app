package com.example.musicapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.musicapp.base.BaseAdapterRCV
import com.example.musicapp.databinding.ItemSingerResultBinding
import com.example.musicapp.model.Singer

class SingerResultAdapter : BaseAdapterRCV<Singer, ItemSingerResultBinding>() {
    override fun bindData(binding: ItemSingerResultBinding, item: Singer, position: Int) {
        binding.tvSinger.text = item.fullname
        Glide.with(binding.imgSinger.context).load(item.avatar).into(binding.imgSinger)
    }

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ItemSingerResultBinding {
        return ItemSingerResultBinding.inflate(inflater, parent, false)
    }
}