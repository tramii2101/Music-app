package com.example.musicapp.utils.extensions

import android.content.Context
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.CheckBox
import android.widget.EditText
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Click the checkbox to show/hide password in edt
 */
fun CheckBox.showHidePassword(edt: EditText) {
    if (this.isChecked) {
        edt.transformationMethod =
            HideReturnsTransformationMethod.getInstance()
    } else {
        edt.transformationMethod =
            PasswordTransformationMethod.getInstance()
    }
}

fun RecyclerView.setLinearLayoutManager(
    context: Context,
    adapter: RecyclerView.Adapter<*>,
    orientation: Int = RecyclerView.VERTICAL
) {
    val manager = LinearLayoutManager(context)
    manager.orientation = orientation
    this.layoutManager = manager
    this.adapter = adapter
}

fun RecyclerView.setGridLayoutManager(
    context: Context,
    adapter: RecyclerView.Adapter<*>,
    orientation: Int = RecyclerView.VERTICAL,
    spanCount: Int
) {
    val manager = GridLayoutManager(context, spanCount)
    manager.orientation = orientation
    this.layoutManager = manager
    this.adapter = adapter
}