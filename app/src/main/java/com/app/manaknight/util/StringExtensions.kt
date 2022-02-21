package com.app.manaknight.util

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.widget.EditText

fun String.checkIsEmpty(): Boolean =
    isNullOrEmpty() || "" == this || this.equals("null", ignoreCase = true)

fun EditText.textToString(): String = this.text.toString()

fun EditText.checkIsEmpty(): Boolean =
    text == null || "" == textToString() || text.toString().equals("null", ignoreCase = true)

fun EditText.isNotValidEmail(): Boolean {
    return !TextUtils.isEmpty(this.text) && Patterns.EMAIL_ADDRESS.matcher(this.text).matches()
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}