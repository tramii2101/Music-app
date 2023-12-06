package com.example.musicapp.ui.authen

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.musicapp.R
import com.example.musicapp.base.BaseFragment
import com.example.musicapp.databinding.FragmentLoginBinding
import com.example.musicapp.utils.common.Screen
import com.example.musicapp.utils.extensions.showHidePassword
import com.example.musicapp.viewmodel.LoginViewModel

class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    private val viewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }
    private val sharedPreferences by lazy {
        requireActivity().getSharedPreferences("AUTHENTICATION", Context.MODE_PRIVATE)
    }
    lateinit var username: String
    lateinit var password: String

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

        binding.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.checkboxVisibility.showHidePassword(binding.edtPassword)        // click icon visibility to show/hide password of edt

        binding.tvSignUp.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frame_authen, RegisterFragment()).commit()
        }


        binding.btnSignIn.setOnClickListener {
            username = binding.edtUsername.text.toString()
            password = binding.edtPassword.text.toString()
            viewModel.getAccessToken(username, password)
            val progressDialog =
                ProgressDialog.show(context, null, "Please wait...", true)
            progressDialog.setCanceledOnTouchOutside(true)
            viewModel.loading.observe(this, Observer {
                //hide dialog
                if (!it) {
                    progressDialog.dismiss()
                    if (viewModel.accessToken != "") {
                        sharedPreferences.edit()
                            .putString("accessToken", "Bear ${viewModel.accessToken}").apply()
                        Toast.makeText(requireContext(), viewModel.accessToken, Toast.LENGTH_SHORT)
                            .show()
                        if (binding.checkboxRemember.isChecked) {
                            sharedPreferences.edit().putString("username", username).apply()
                            sharedPreferences.edit().putString("password", password).apply()
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            viewModel.message,
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }

            })
        }
    }
}