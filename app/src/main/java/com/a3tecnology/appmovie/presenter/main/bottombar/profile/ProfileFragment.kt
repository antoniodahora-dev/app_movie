package com.a3tecnology.appmovie.presenter.main.bottombar.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.a3tecnology.appmovie.R
import com.a3tecnology.appmovie.databinding.BottomSheetLogoutMovieBinding
import com.a3tecnology.appmovie.databinding.FragmentProfileBinding
import com.a3tecnology.appmovie.domain.model.menu.MenuProfile
import com.a3tecnology.appmovie.domain.model.menu.MenuProfileType.*
import com.a3tecnology.appmovie.domain.model.user.User
import com.a3tecnology.appmovie.presenter.auth.activity.AuthActivity
import com.a3tecnology.appmovie.presenter.auth.activity.AuthActivity.Companion.AUTHENTICATION_PARAMETER
import com.a3tecnology.appmovie.presenter.auth.enums.AuthenticationDestinations
import com.a3tecnology.appmovie.presenter.main.bottombar.profile.adapter.ProfileMenuAdapter
import com.a3tecnology.appmovie.util.FirebaseHelp
import com.a3tecnology.appmovie.util.StateView
import com.a3tecnology.appmovie.util.showSnackBar
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

//aula 363
@AndroidEntryPoint
class ProfileFragment : Fragment() {


    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!

    private lateinit var profileMenuAdapter: ProfileMenuAdapter

    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUser()
        initRecyclerView()

    }

    private fun getUser() {
        profileViewModel.getUser().observe(viewLifecycleOwner) {stateView ->
            when(stateView) {
                is StateView.Loading -> {}

                is StateView.Success -> {
                    stateView.data?.let {
                        configData(user = it)
                    }
                }

                is StateView.Error -> {
                    showSnackBar(
                        message = FirebaseHelp.validatorError(error = stateView.message ?: "")
                    )
                }
            }
        }
    }

    private fun initRecyclerView() {
        profileMenuAdapter = ProfileMenuAdapter(
            items = MenuProfile.items,
            context = requireContext(),
            onClick = { type ->
                when (type) {
                    PROFILE -> {
                        findNavController().navigate(R.id.action_menu_profile_to_editProfileFragment)
                    }
                    NOTIFICATION -> {}

                    //aula 364
                    DOWNLOAD -> {
                        val bottomNavigation =
                            activity?.findViewById<BottomNavigationView>(R.id.bottomView)
                        bottomNavigation?.selectedItemId = R.id.menu_download
                    }

                    SECURITY -> {}
                    LANGUAGE -> {}
                    DARK_MODE -> {}
                    HELP -> {}
                    PRIVACY_POLICY -> {}
                    LOGOUT -> {
                        showBottomSheetLogoutMovie()
                    }
                }
            }
        )

        with(binding.recyclerItem) {
            setHasFixedSize(true)
            adapter = profileMenuAdapter
        }

    }

    private fun configData(user: User) {
        binding.txtNameProfile.text = getString(
            R.string.txt_user_photo_empty_fragment ,
            user.firstName,
            user.surName
        )

        binding.txtEmailProfile.text = FirebaseHelp.getAuth().currentUser?.email

        // aula 375
        binding.txtPhotoEmpty.isVisible = user.photoUrl?.isEmpty() == true
        binding.imgProfile.isVisible = user.photoUrl?.isNotEmpty() == true

        if (user.photoUrl?.isNotEmpty() == true) {
            Glide
                .with(requireContext())
                .load(user.photoUrl)
                .into(binding.imgProfile)
        } else {
            binding.txtPhotoEmpty.text = getString(
                R.string.txt_user_photo_empty_fragment , user.firstName?.first(), user.surName?.first())
        }
    }

    //aula 365 - 110
    private fun showBottomSheetLogoutMovie() {

        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        val bottomSheetBinding = BottomSheetLogoutMovieBinding.inflate(
            layoutInflater, null, false)

        bottomSheetBinding.btnCancel.setOnClickListener { bottomSheetDialog.dismiss() }
        bottomSheetBinding.btnConfirm.setOnClickListener {
            bottomSheetDialog.dismiss()
            logout()
        }

        bottomSheetDialog.setContentView(bottomSheetBinding.root)
        bottomSheetDialog.show()
    }

    //aula 365 - 110
    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        activity?.finish()
        val intent = Intent(requireContext(), AuthActivity::class.java)
        intent.putExtra(AUTHENTICATION_PARAMETER, AuthenticationDestinations.LOGIN_SCREEN)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}