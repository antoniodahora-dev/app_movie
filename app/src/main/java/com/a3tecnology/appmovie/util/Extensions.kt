package com.a3tecnology.appmovie.util

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.a3tecnology.appmovie.R
import com.google.android.material.snackbar.Snackbar



fun Fragment.initToolbar(
    toolbar: Toolbar,
    showIconNavigation:
    Boolean = true,
    lightIcon: Boolean = false
) {

    val iconBack = if (lightIcon) R.drawable.ic_back_white else R.drawable.ic_back

    (activity as AppCompatActivity).setSupportActionBar(toolbar)
    (activity as AppCompatActivity).title = ""

    if (showIconNavigation) {
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(iconBack)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    toolbar.setNavigationOnClickListener {
        activity?.onBackPressedDispatcher?.onBackPressed() // forma atualizada
    }
}

// Toolbar the Views
fun Fragment.hideKeyboard() { //hide keyboard
    val view = activity?.currentFocus
    if (view != null) {
        val imn = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imn.hideSoftInputFromWindow(view.windowToken, 0)
        view.clearFocus()
    }
}

fun String.isEmailValid(): Boolean { // verify - validation email
    val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
    return emailPattern.matches(this)
}

// exibir/tratar as mensagens de error
fun Fragment.showSnackBar(
    message: Int,
    duration: Int = Snackbar.LENGTH_SHORT
) {
    view?.let {
        Snackbar.make(it, message, duration).show()
    }
}

