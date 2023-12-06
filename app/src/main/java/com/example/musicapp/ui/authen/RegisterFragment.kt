package com.example.musicapp.ui.authen

import android.app.ProgressDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.musicapp.R
import com.example.musicapp.base.BaseFragment
import com.example.musicapp.databinding.FragmentRegisterBinding
import com.example.musicapp.utils.common.RegexCommon
import com.example.musicapp.utils.extensions.showHidePassword
import com.example.musicapp.viewmodel.RegisterViewModel

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    lateinit var username: String
    lateinit var email: String
    lateinit var password: String
    lateinit var passwordAgain: String
    lateinit var fullname: String

    val viewModel by lazy {
        ViewModelProvider(this)[RegisterViewModel::class.java]
    }

    private val sharedPreferences by lazy {
        requireActivity().getSharedPreferences("AUTHENTICATION", Context.MODE_PRIVATE)
    }

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterBinding {
        return FragmentRegisterBinding.inflate(inflater, container, false)
    }

    override fun initView() {

    }

    override fun bindData() {

    }

    override fun handleEvent() {

        binding.checkboxPassword.showHidePassword(binding.edtPassword)
        binding.checkboxRetypePassword.showHidePassword(binding.edtConfirmPassword)

        binding.tvSignIn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frame_authen, LoginFragment()).commit()
        }

        binding.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.btnSignUp.setOnClickListener {
            fullname = binding.edtFullName.text.toString()
            username = binding.edtUsername.text.toString()
            email = binding.edtEmail.text.toString()
            password = binding.edtPassword.text.toString()
            passwordAgain = binding.edtConfirmPassword.text.toString()

            if (validateInput()) {
                val progressDialog =
                    ProgressDialog.show(context, null, "Registering, please wait...", true)
                progressDialog.setCanceledOnTouchOutside(true)
                viewModel.register(fullname, username, email, password, passwordAgain)
                viewModel.loading.observe(this) {
                    if (!it) {// it = viewModel.loading
                        progressDialog.dismiss()
                        if (viewModel.status.value in 200..299) {
                            Toast.makeText(requireContext(), viewModel.message, Toast.LENGTH_SHORT)
                                .show()
                            requireActivity().supportFragmentManager.beginTransaction()
                                .replace(R.id.frame_authen, LoginFragment()).commit()
                        } else {

                            Toast.makeText(requireContext(), viewModel.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }

        }
    }

    private fun validateInput(): Boolean {
        var err = 0
        if (!RegexCommon.EMAIL.matches(email)) {
            binding.edtEmail.error = "Email is invalid"
            err++
        }
        if (!RegexCommon.USERNAME.matches(username)) {
            binding.edtUsername.error = "Username is invalid"
            err++
        }
        if (!RegexCommon.PASSWORD.matches(password)) {
            binding.edtPassword.error = "Invalid password"
            err++
        }
        if (passwordAgain != password) {
            binding.edtConfirmPassword.error = "Password is incorrect"
            err++
        }
        return err <= 0
    }


}