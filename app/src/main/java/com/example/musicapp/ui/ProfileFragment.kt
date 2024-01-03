package com.example.musicapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.musicapp.R
import com.example.musicapp.base.BaseFragment
import com.example.musicapp.databinding.FragmentProfileBinding
import com.example.musicapp.utils.common.ApiConstants
import com.example.musicapp.viewmodel.UserViewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val sharedPreferences by lazy {
        requireActivity().getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[UserViewModel::class.java]
    }

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater, container, false)
    }

    override fun initView() {

    }

    override fun bindData() {
        val accessToken = sharedPreferences.getString("accessToken", null)
        if (accessToken != null) {
            viewModel.getUserProfile("Bearer $accessToken")
            viewModel.loading.observe(this) {
                if (!it && viewModel.user != null) {
                    binding.tvFullname.text = viewModel.user!!.fullname
                    binding.tvUsername.text = viewModel.user!!.username
                    Glide.with(binding.ivAvt.context)
                        .load(ApiConstants.BASE_URL + viewModel.user!!.avatar).into(binding.ivAvt)
                    binding.tvEmail.text = viewModel.user!!.email
                    binding.tvDob.text = viewModel.user!!.dateOfBirth.substring(0, 10)
                    binding.tvGender.text = viewModel.user!!.gender
                }
            }
        }
    }

    override fun handleEvent() {
        binding.btnEdit.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, EditProfileFragment()).addToBackStack(null)
                .commit()
        }
    }

}