package com.example.musicapp.ui.authen

import android.view.LayoutInflater
import com.example.musicapp.R
import com.example.musicapp.base.BaseActivity
import com.example.musicapp.databinding.ActivityAuthenticationBinding

class AuthenticationActivity : BaseActivity<ActivityAuthenticationBinding>() {
    override fun inflateViewBinding(layoutInflater: LayoutInflater): ActivityAuthenticationBinding {
        return ActivityAuthenticationBinding.inflate(layoutInflater)
    }

    override fun initView() {
        supportFragmentManager.beginTransaction().replace(R.id.frame_authen, LoginFragment())
            .commit()
    }

    override fun bindData() {

    }

    override fun handleEvent() {

    }
}