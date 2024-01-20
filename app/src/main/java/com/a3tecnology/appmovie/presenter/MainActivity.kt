package com.a3tecnology.appmovie.presenter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.a3tecnology.appmovie.R
import com.a3tecnology.appmovie.databinding.ActivityMainBinding
import com.a3tecnology.appmovie.presenter.auth.forgot.ForgotFragment
import com.a3tecnology.appmovie.presenter.auth.login.LoginFragment
import com.a3tecnology.appmovie.presenter.auth.register.RegisterFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val registerFragment = LoginFragment()

        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.add(R.id.container, registerFragment)
        transaction.commit()
    }
}