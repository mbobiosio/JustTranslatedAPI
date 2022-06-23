package com.mbobiosio.justtranslatedapi.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun getLanguageCode(language: String): String {
    var fullLanguage = language
    if (fullLanguage.length > 3) {
        fullLanguage = fullLanguage.substring(fullLanguage.length - 3, fullLanguage.length)
    }
    return fullLanguage
}

fun View.hideKeyboard() {
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(windowToken, 0)
}
