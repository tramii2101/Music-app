package com.example.musicapp.ui.authen

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.musicapp.R
import com.example.musicapp.base.BaseFragment
import com.example.musicapp.databinding.FragmentForgotPasswordMethodBinding
import com.example.musicapp.utils.common.RegexCommon
import com.example.musicapp.viewmodel.ForgotPasswordViewModel

class ForgotPasswordMethodFragment : BaseFragment<FragmentForgotPasswordMethodBinding>() {

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[ForgotPasswordViewModel::class.java]
    }

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentForgotPasswordMethodBinding {
        return FragmentForgotPasswordMethodBinding.inflate(inflater, container, false)
    }

    override fun initView() {

    }

    override fun bindData() {

    }

    override fun handleEvent() {
        binding.tvSend.setOnClickListener {
            val email = binding.edtEmail.text.toString().trim()

            if (RegexCommon.EMAIL.matches(email)) {
                viewModel.enterEmail(email)
                viewModel.success.observe(this) {
                    if (viewModel.success.value == true) {
                        findNavController().navigate(R.id.action_forgotPasswordMethodFragment_to_enterOtpFragment)
                    }
                }

                viewModel.hasError.observe(this) {
                    if (viewModel.hasError.value == true) {
                        Toast.makeText(context, viewModel.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }



        binding.tvBack.setOnClickListener {
//            findNavController().navigate(R.id.action_forgotPasswordMethodFragment_to_loginFragment)
            requireActivity().supportFragmentManager.popBackStack()
        }
    }


}