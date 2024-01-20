package com.a3tecnology.appmovie.presenter.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.a3tecnology.appmovie.R
import com.a3tecnology.appmovie.databinding.FragmentLoginBinding
import com.a3tecnology.appmovie.util.StateView
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    private val binding get() = _binding!!

    private val loginViewModel : LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }
    private fun initListeners() {
        binding.btnLogin.setOnClickListener { validateData() }

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
                login(email, password)
            } else {

            }

        } else {

        }
    }

    private fun login(email: String, password: String) {
        loginViewModel.login(email, password).observe(viewLifecycleOwner) {stateView ->
            when(stateView) {
                is StateView.Loading -> {
                    binding.progressLoadingLogin.isVisible = true
                }

                is StateView.Success -> {
                    binding.progressLoadingLogin.isVisible = false
                    Toast.makeText(requireContext(), "Login com Sucesso.", Toast.LENGTH_SHORT).show()
                }

                is StateView.Error -> {
                    binding.progressLoadingLogin.isVisible = false
                    Toast.makeText(requireContext(), stateView.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}