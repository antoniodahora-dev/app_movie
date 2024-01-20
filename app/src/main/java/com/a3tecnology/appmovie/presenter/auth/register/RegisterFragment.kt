package com.a3tecnology.appmovie.presenter.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.a3tecnology.appmovie.R
import com.a3tecnology.appmovie.databinding.FragmentRegisterBinding
import com.a3tecnology.appmovie.util.StateView
import com.a3tecnology.appmovie.util.hideKeyboard
import com.a3tecnology.appmovie.util.initToolbar
import com.a3tecnology.appmovie.util.isEmailValid
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val registerViewModel : RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar(toolbar = binding.toolbar)
        initListeners()
    }

    private fun initListeners() {
        binding.btnRegister.setOnClickListener { validateData() }

        Glide
            .with(requireContext())
            .load(R.drawable.loading)
            .into(binding.progressLoadingRegister)
    }

    private fun validateData() {
        val email = binding.editEmailRegister.text.toString()
        val password = binding.editPasswordRegister.text.toString()

        if (email.isEmailValid()) {
            if (password.isNotEmpty()) {
                hideKeyboard()
                register(email, password)
            } else {
                Toast.makeText(requireContext(), "Email invalido.", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(requireContext(), "Email invalido.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun register(email: String, password: String) {
        registerViewModel.register(email, password).observe(viewLifecycleOwner) {stateView ->
            when(stateView) {
                is StateView.Loading -> {
                    binding.progressLoadingRegister.isVisible = true
                }

                is StateView.Success -> {
                    binding.progressLoadingRegister.isVisible = false
                    Toast.makeText(requireContext(), "Salvo com Sucesso.", Toast.LENGTH_SHORT).show()
                }

                is StateView.Error -> {
                    binding.progressLoadingRegister.isVisible = false
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