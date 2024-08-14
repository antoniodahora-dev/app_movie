package com.a3tecnology.appmovie.presenter.main.bottombar.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.a3tecnology.appmovie.R
import com.a3tecnology.appmovie.databinding.FragmentHomeBinding
import com.a3tecnology.appmovie.databinding.FragmentProfileBinding
import com.a3tecnology.appmovie.domain.model.MenuProfile
import com.a3tecnology.appmovie.domain.model.MenuProfileType
import com.a3tecnology.appmovie.domain.model.MenuProfileType.*
import com.a3tecnology.appmovie.presenter.main.bottombar.profile.adapter.ProfileMenuAdapter
import dagger.hilt.android.AndroidEntryPoint

//aula 363
@AndroidEntryPoint
class ProfileFragment : Fragment() {


    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!

    private lateinit var profileMenuAdapter: ProfileMenuAdapter

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

        configData()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        profileMenuAdapter = ProfileMenuAdapter(
            items = MenuProfile.items,
            context = requireContext(),
            onClick = { type ->
                when (type) {
                    PROFILE -> {}
                    NOTIFICATION -> {}
                    DOWNLOAD -> {}
                    SECURITY -> {}
                    LANGUAGE -> {}
                    DARK_MODE -> {}
                    HELP -> {}
                    PRIVACY_POLICY -> {}
                    LOGOUT -> {}
                }
            }
        )

        with(binding.recyclerItem) {
            setHasFixedSize(true)
            adapter = profileMenuAdapter
        }

    }

    private fun configData() {
        binding.imgProfile.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_profile
            )
        )

        binding.txtNameProfile.text = "Antonio da Hora"
        binding.txtEmailProfile.text = "antonio_dahora@hotmail.com"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}