package com.example.musicapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.R
import com.example.musicapp.adapters.CategoryAdapter
import com.example.musicapp.base.BaseFragment
import com.example.musicapp.databinding.FragmentAllCategoryBinding
import com.example.musicapp.utils.extensions.setGridLayoutManager
import com.example.musicapp.viewmodel.CategoryViewModel


class AllCategoryFragment : BaseFragment<FragmentAllCategoryBinding>() {

    private val sharedPreferences by lazy {
        requireActivity().getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
    }

    private val categoryAdapter by lazy {
        CategoryAdapter()
    }

    private val categoryViewModel by lazy {
        ViewModelProvider(this)[CategoryViewModel::class.java]
    }

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAllCategoryBinding {
        return FragmentAllCategoryBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        binding.recyclerCategory.setGridLayoutManager(
            requireContext(),
            categoryAdapter,
            RecyclerView.VERTICAL,
            2
        )
    }

    override fun bindData() {
        val accessToken = sharedPreferences.getString("accessToken", "")

        categoryViewModel.getListCategories("Bearer $accessToken")
        categoryViewModel.loading.observe(this) {
            if (!it && categoryViewModel.categories.isNotEmpty()) {
                categoryAdapter.setDataList(categoryViewModel.categories)
            }
        }
    }

    override fun handleEvent() {
        categoryAdapter.setOnClickItem { item, _ ->
            if (item != null) {
                sharedPreferences.edit().putString("categoryId", item.id).apply()
                sharedPreferences.edit().putString("categoryName", item.name).apply()
                findNavController().navigate(R.id.action_allCategoryFragment_to_categoryFragment)
            }
        }

        binding.imgBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

}