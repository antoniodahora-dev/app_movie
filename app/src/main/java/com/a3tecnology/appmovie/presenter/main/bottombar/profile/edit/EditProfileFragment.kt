package com.a3tecnology.appmovie.presenter.main.bottombar.profile.edit

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.a3tecnology.appmovie.R
import com.a3tecnology.appmovie.databinding.BottomSheetSelectImageBinding
import com.a3tecnology.appmovie.databinding.FragmentEditProfileBinding
import com.a3tecnology.appmovie.domain.model.user.User
import com.a3tecnology.appmovie.presenter.main.activity.MainActivity
import com.a3tecnology.appmovie.util.FirebaseHelp
import com.a3tecnology.appmovie.util.StateView
import com.a3tecnology.appmovie.util.initToolbar
import com.a3tecnology.appmovie.util.showSnackBar
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

//aula 364

@AndroidEntryPoint
class EditProfileFragment : Fragment() {

    private var _binding : FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    private val editProfileViewModel: EditProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar(toolbar = binding.toolbar)
        initListener()
        getUser()
    }

    //Aula 366-111
    private fun validateData() {
        val name = binding.editNameProfile.text.toString()
        val surName = binding.editSurNameProfile.text.toString()
        val phone = binding.editPhoneProfile.text.toString()
        val genre = binding.editGenreProfile.text.toString()
        val country = binding.editCountryProfile.text.toString()

        if (name.isEmpty()) {
            showSnackBar(message = R.string.txt_name_empty_edit_profile_fragment)
        }

       else if (surName.isEmpty()) {
            showSnackBar(message = R.string.txt_surnane_empty_edit_profile_fragment)
        }

        else if (phone.isEmpty()) {
            showSnackBar(message = R.string.txt_phone_invalid_edit_profile_fragment)
        }

        else if (genre.isEmpty()) {
            showSnackBar(message = R.string.txt_genre_empty_edit_profile_fragment)
        }

        else if (country.isEmpty()) {
            showSnackBar(message = R.string.txt_country_empty_edit_profile_fragment)
        }

        // aula 367.112
        val user = User(
            firstName = name,
            surName = surName,
            email = FirebaseHelp.getAuth().currentUser?.email,
            phone = phone,
            genre = genre,
            country = country
        )

        update(user)
    }

    private fun initListener() {
        binding.btnUpdate.setOnClickListener {
            validateData()
        }

        //aula 369
        binding.imgEditProfile.setOnClickListener{
            openBottomSelectImage()
        }
    }

    //aula 367.112
    private fun update(user: User) {
        editProfileViewModel.update(user).observe(viewLifecycleOwner) {stateView ->
            when(stateView) {
                is StateView.Loading -> {
                    showLoading(true)
                }

                is StateView.Success -> {

                    showLoading(false)
                    showSnackBar(message = R.string.txt_update_message_edit_profile_success)
//                    Toast.makeText(requireContext(), "Cadastro realizado com sucesso.", Toast.LENGTH_SHORT).show()
                }

                is StateView.Error -> {
                    showLoading(false)
                    showSnackBar(
                        message = FirebaseHelp.validatorError(error = stateView.message ?: "")
                    )
                }
            }
        }
    }

    //aula 367.112
    private fun showLoading(isLoading: Boolean) {

        if (isLoading) {
            Glide
                .with(requireContext())
                .load(R.drawable.loading)
                .into(binding.progressLoadingUpdate)

            binding.progressLoadingUpdate.isVisible = true
        } else {
            binding.progressLoadingUpdate.isVisible = false
        }
    }

    //aula 367.113
    private fun getUser() {
        editProfileViewModel.getUser().observe(viewLifecycleOwner) {stateView ->
            when(stateView) {
                is StateView.Loading -> {
                    showLoading(true)
                }

                is StateView.Success -> {

                    showLoading(false)
                    stateView.data?.let {
                        configData(user = it)
                    }
//                    Toast.makeText(requireContext(), "Cadastro realizado com sucesso.", Toast.LENGTH_SHORT).show()
                }

                is StateView.Error -> {
                    showLoading(false)
                    showSnackBar(
                        message = FirebaseHelp.validatorError(error = stateView.message ?: "")
                    )
                }
            }
        }
    }

    //aula 367.112
    private fun configData(user: User) {
       binding.editNameProfile.setText(user.firstName)
       binding.editSurNameProfile.setText(user.surName)
       binding.editEmailProfile.setText(FirebaseHelp.getAuth().currentUser?.email)
       binding.editPhoneProfile.setText(user.phone)
       binding.editGenreProfile.setText(user.genre)
       binding.editCountryProfile.setText(user.country)

    }


    // aula 369
    private fun openBottomSelectImage() {
            val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
            val bottomSheetBinding = BottomSheetSelectImageBinding.inflate(
                layoutInflater, null, false)

            bottomSheetBinding.btnCamera.setOnClickListener {
                bottomSheetDialog.dismiss()
                //openCamera
            }


            bottomSheetBinding.btnGallery.setOnClickListener {
                bottomSheetDialog.dismiss()
                //openGalery
            }

            bottomSheetDialog.setContentView(bottomSheetBinding.root)
            bottomSheetDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}