package com.example.musicapp.ui.authen

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.musicapp.R
import com.example.musicapp.base.BaseFragment
import com.example.musicapp.databinding.FragmentLoginBinding
import com.example.musicapp.utils.extensions.showHidePassword
import com.example.musicapp.viewmodel.LoginViewModel

class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    private val viewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }
    private val sharedPreferences by lazy {
        requireActivity().getSharedPreferences("AUTHENTICATION", Context.MODE_PRIVATE)
    }

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, false)
    }

    override fun initView() {

    }

    override fun bindData() {

    }

    override fun handleEvent() {

        binding.checkboxVisibility.showHidePassword(binding.edtPassword)        // click icon visibility to show/hide password of edt

        binding.tvSignUp.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frame_authen, RegisterFragment()).commit()
        }


        binding.btnSignIn.setOnClickListener {
            viewModel.username = binding.edtUsername.text.toString()
            viewModel.password = binding.edtPassword.text.toString()
            viewModel.getAccessToken()

            viewModel.loading.observe(this, Observer {
                if (viewModel.loading.value == true) {
                    //show dialog
                } else {
                    //hide dialog
                    if (viewModel.accessToken != "") {
                        sharedPreferences.edit().putString("accessToken", "Bear ${viewModel.accessToken}").apply()
                        Toast.makeText(requireContext(), viewModel.accessToken, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), viewModel.message.toString(), Toast.LENGTH_SHORT).show()

                    }
                }
            })
        }
    }
}