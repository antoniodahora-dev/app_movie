package com.a3tecnology.appmovie.presenter.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.a3tecnology.appmovie.R
import com.a3tecnology.appmovie.databinding.FragmentLoginBinding
import com.a3tecnology.appmovie.presenter.main.activity.MainActivity
import com.a3tecnology.appmovie.util.FirebaseHelp
import com.a3tecnology.appmovie.util.StateView
import com.a3tecnology.appmovie.util.hideKeyboard
import com.a3tecnology.appmovie.util.initToolbar
import com.a3tecnology.appmovie.util.isEmailValid
import com.a3tecnology.appmovie.util.onNavigate
import com.a3tecnology.appmovie.util.showSnackBar
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar(toolbar = binding.toolbar)
        initListeners()
    }

    private fun initListeners() {
        binding.btnLogin.setOnClickListener { validateData() }
        binding.btnForgotLogin.setOnClickListener {
            findNavController().onNavigate(R.id.action_loginFragment_to_forgotFragment)
        }

        Glide
            .with(requireContext())
            .load(R.drawable.loading)
            .into(binding.progressLoadingLogin)
    }

    private fun validateData() {
        val email = binding.editEmailLogin.text.toString()
        val password = binding.editPasswordLogin.text.toString()

        if (email.isNotEmpty()) {

            if (password.isNotEmpty()) {

                if (email.isEmailValid()) {

                    if (password.isNotEmpty()) {

                        hideKeyboard()
                        login(email, password)

                    } else {
                        showSnackBar(message = R.string.txt_password_empty_login_fragment)
                    }
                } else {
                    showSnackBar(message = R.string.txt_email_login_invalid)
                }
            } else {
                showSnackBar(message = R.string.txt_email_or_password_empty)
            }
        } else {
            showSnackBar(message = R.string.txt_email_or_password_empty)
        }
    }

    private fun login(email: String, password: String) {
        loginViewModel.login(email, password).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.progressLoadingLogin.isVisible = true
                }

                is StateView.Success -> {
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()
//                    binding.progressLoadingLogin.isVisible = false
                }

                is StateView.Error -> {
                    binding.progressLoadingLogin.isVisible = false
                    showSnackBar(
                        message = FirebaseHelp.validatorError(error = stateView.message ?: "")
                    )
//                    Toast.makeText(requireContext(), stateView.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}