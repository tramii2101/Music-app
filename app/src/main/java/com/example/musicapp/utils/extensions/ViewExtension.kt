package com.example.musicapp.utils.extensions

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.CheckBox
import android.widget.EditText

/**
 * Click the checkbox to show/hide password in edt
 */
fun CheckBox.showHidePassword(edt: EditText) {
    if (this.isChecked) {
        edt.transformationMethod =
            PasswordTransformationMethod.getInstance()
    } else {
        edt.transformationMethod =
            HideReturnsTransformationMethod.getInstance()
    }
}