package com.example.musicapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.musicapp.base.BaseAdapterRCV
import com.example.musicapp.databinding.ItemCategoryBinding
import com.example.musicapp.model.Category

class CategoryAdapter : BaseAdapterRCV<Category, ItemCategoryBinding>() {
    override fun bindData(binding: ItemCategoryBinding, item: Category, position: Int) {
        Glide.with(binding.imgCategory.context).load(item.image).into(binding.imgCategory)
        binding.tvCategoryName.text = item.name
    }

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ItemCategoryBinding {
        return ItemCategoryBinding.inflate(inflater, parent, false)
    }
}