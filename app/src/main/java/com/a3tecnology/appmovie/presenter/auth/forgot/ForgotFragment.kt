package com.a3tecnology.appmovie.presenter.auth.forgot

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.a3tecnology.appmovie.R
import com.a3tecnology.appmovie.databinding.FragmentForgotBinding
import com.a3tecnology.appmovie.util.StateView
import com.a3tecnology.appmovie.util.hideKeyboard
import com.a3tecnology.appmovie.util.initToolbar
import com.a3tecnology.appmovie.util.isEmailValid
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotFragment : Fragment() {

    private var _binding : FragmentForgotBinding? = null

    private val binding get() = _binding!!

    private val forgotViewModel : ForgotViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentForgotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar(toolbar = binding.toolbar)
        initListeners()
    }

    private fun initListeners() {
        binding.btnForgot.setOnClickListener { validateData() }

        Glide
            .with(requireContext())
            .load(R.drawable.loading)
            .into(binding.progressLoadingForgot)
    }

    private fun validateData() {
        val email = binding.editEmailForgot.text.toString()

        if (email.isEmailValid()) {

            hideKeyboard()
            forgot(email)

        } else {
            Toast.makeText(requireContext(), "Email invalido.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun forgot(email: String) {
        forgotViewModel.forgot(email).observe(viewLifecycleOwner) {stateView ->
            when(stateView) {
                is StateView.Loading -> {
                    binding.progressLoadingForgot.isVisible = true
                }

                is StateView.Success -> {
                    binding.progressLoadingForgot.isVisible = false
                    Toast.makeText(requireContext(), "Email enviado com Sucesso.", Toast.LENGTH_SHORT).show()
                }

                is StateView.Error -> {
                    binding.progressLoadingForgot.isVisible = false
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