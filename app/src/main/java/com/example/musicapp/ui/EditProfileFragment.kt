package com.example.musicapp.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.musicapp.base.BaseFragment
import com.example.musicapp.databinding.FragmentEditProfileBinding
import com.example.musicapp.viewmodel.UserViewModel


open class EditProfileFragment : BaseFragment<FragmentEditProfileBinding>() {
    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[UserViewModel::class.java]
    }

    private val sharedPreferences by lazy {
        requireActivity().getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
    }

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEditProfileBinding {
        return FragmentEditProfileBinding.inflate(inflater, container, false)
    }

    override fun initView() {


    }

    override fun bindData() {
        binding.edtFullName.hint = viewModel.user?.fullname
        binding.edtEmail.hint = viewModel.user?.email
        binding.edtDob.hint = viewModel.user?.dateOfBirth
        binding.edtGender.hint = viewModel.user?.gender
    }

    override fun handleEvent() {
        val accessToken = sharedPreferences.getString("accessToken", null)
        Log.e(accessToken, "$accessToken")

        binding.btnUpdate.setOnClickListener {
            val fullname by lazy {
                if (binding.edtFullName.text.toString().isNullOrEmpty()) {
                    viewModel.user!!.fullname
                } else {
                    binding.edtFullName.text.toString()
                }
            }

            val email by lazy {
                if (binding.edtEmail.text.toString().isNullOrEmpty()) {
                    viewModel.user!!.email
                } else {
                    binding.edtEmail.text.toString()
                }
            }

            val gender by lazy {
                if (binding.edtGender.text.toString().isNullOrEmpty()) {
                    viewModel.user!!.gender
                } else {
                    binding.edtGender.text.toString()
                }
            }

            val dob by lazy {
                if (binding.edtDob.text.toString().isNullOrEmpty()) {
                    viewModel.user!!.dateOfBirth
                } else {
                    binding.edtDob.text.toString()
                }
            }

            viewModel.updateUserProfile(
                "Bearer ${accessToken!!}",
                fullname,
                email,
                gender,
                dob
            )


            viewModel.loading.observe(this) {
                if (!it) {
                    Toast.makeText(context, viewModel.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}