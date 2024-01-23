package com.a3tecnology.appmovie.presenter.auth.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.a3tecnology.appmovie.R
import com.a3tecnology.appmovie.databinding.FragmentRegisterBinding
import com.a3tecnology.appmovie.presenter.main.activity.MainActivity
import com.a3tecnology.appmovie.util.FirebaseHelp
import com.a3tecnology.appmovie.util.StateView
import com.a3tecnology.appmovie.util.hideKeyboard
import com.a3tecnology.appmovie.util.initToolbar
import com.a3tecnology.appmovie.util.isEmailValid
import com.a3tecnology.appmovie.util.showSnackBar
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

        if (email.isNotEmpty()) {

            if (password.isNotEmpty()) {

                if (email.isEmailValid()) {

                    if (password.isNotEmpty()) {

                        hideKeyboard()
                        register(email, password)

                    } else {
                        showSnackBar(message = R.string.txt_password_empty_login_fragment )
                    }

                } else {
                    showSnackBar(message = R.string.txt_email_login_invalid )
                }
            } else {
                showSnackBar(message = R.string.txt_email_or_password_empty)
            }
        } else {
            showSnackBar(message = R.string.txt_email_or_password_empty)
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

                   startActivity(Intent(requireContext(), MainActivity::class.java))
//                    requireActivity().finish()

                    showSnackBar(message = R.string.txt_register_fragment_success)
//                    Toast.makeText(requireContext(), "Cadastro realizado com sucesso.", Toast.LENGTH_SHORT).show()
                }

                is StateView.Error -> {
                    binding.progressLoadingRegister.isVisible = false
                    showSnackBar(
                        message = FirebaseHelp.validatorError(error = stateView.message ?: "")
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}