package com.a3tecnology.appmovie.presenter.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.a3tecnology.appmovie.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }
}