package com.example.musicapp.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB: ViewBinding> : AppCompatActivity() {
    protected lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = inflateViewBinding(layoutInflater)
        setContentView(binding.root)
        initView()
        bindData()
        handleEvent()
    }

    abstract fun inflateViewBinding(layoutInflater: LayoutInflater): VB
    abstract fun initView()
    abstract fun bindData()
    abstract fun handleEvent()
}