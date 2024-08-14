package com.a3tecnology.appmovie.presenter.main.bottombar.profile.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.a3tecnology.appmovie.R
import com.a3tecnology.appmovie.databinding.FragmentEditProfileBinding
import com.a3tecnology.appmovie.util.initToolbar
import com.a3tecnology.appmovie.util.showSnackBar

//aula 364
class EditProfileFragment : Fragment() {

    private var _binding : FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

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

    }

    private fun initListener() {
        binding.btnUpdate.setOnClickListener {
            validateData()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}