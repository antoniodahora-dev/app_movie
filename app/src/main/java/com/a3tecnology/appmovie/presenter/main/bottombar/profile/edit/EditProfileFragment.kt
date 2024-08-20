package com.a3tecnology.appmovie.presenter.main.bottombar.profile.edit

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.a3tecnology.appmovie.R
import com.a3tecnology.appmovie.databinding.BottomSheetPermissionDeniedBinding
import com.a3tecnology.appmovie.databinding.BottomSheetSelectImageBinding
import com.a3tecnology.appmovie.databinding.FragmentEditProfileBinding
import com.a3tecnology.appmovie.domain.model.user.User
import com.a3tecnology.appmovie.util.FirebaseHelp
import com.a3tecnology.appmovie.util.StateView
import com.a3tecnology.appmovie.util.initToolbar
import com.a3tecnology.appmovie.util.showSnackBar
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//aula 364

@AndroidEntryPoint
class EditProfileFragment : Fragment() {

    private var _binding : FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    private val editProfileViewModel: EditProfileViewModel by viewModels()

    //aula 370
    private val GALLERY_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE

    //aula 371
    private val CAMERA_PERMISSION = Manifest.permission.CAMERA
    private var currentPhotoPath: String? = null
    private lateinit var photoUri: Uri

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
                checkCameraPermission()
            }

            bottomSheetBinding.btnGallery.setOnClickListener {
                bottomSheetDialog.dismiss()
                checkGalleryPermission()
            }

            bottomSheetDialog.setContentView(bottomSheetBinding.root)
            bottomSheetDialog.show()
    }

    //AULA 370
    private fun checkGalleryPermission() {

        // version android 12 -
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            if (checkPermissionGranted(GALLERY_PERMISSION)) {
                pickImageLauncher.launch("image/*")
            } else {
                galleryPermissionLauncher.launch(GALLERY_PERMISSION)
            }
        } else {
            // version android 13 +
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        uri ->
        if (uri != null) {
            binding.imgProfile.setImageURI(uri)
        }
    }

    //AULA 370
    private fun checkPermissionGranted(permission: String) =
        ContextCompat.checkSelfPermission(
            requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED

    private fun showBottomSheetPermissionDenied() {
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        val bottomSheetBinding = BottomSheetPermissionDeniedBinding.inflate(
            layoutInflater, null, false)

        bottomSheetBinding.btnCancel.setOnClickListener {
            bottomSheetDialog.dismiss()
        }


        bottomSheetBinding.btnAccept.setOnClickListener {
            bottomSheetDialog.dismiss()

            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", requireActivity().packageName, null)

            )
            startActivity(intent)

        }

        bottomSheetDialog.setContentView(bottomSheetBinding.root)
        bottomSheetDialog.show()
    }

    private val galleryPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                pickImageLauncher.launch("image/*")
            } else {
                showBottomSheetPermissionDenied()
            }
        }

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            binding.imgProfile.setImageURI(it)
        }
    }

    //aula 371

    private fun checkCameraPermission() {
        if (checkPermissionGranted(CAMERA_PERMISSION)) {
            openCamera()
        } else {
            cameraPermissionLauncher.launch(CAMERA_PERMISSION)
        }
    }

    private val cameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openCamera()
            } else {
                showBottomSheetPermissionDenied()
            }
        }

    private fun openCamera() {
        val photoFile = createImageFile()
        photoFile?.let {
            photoUri = FileProvider.getUriForFile(
                requireContext(),
                "${requireContext().packageName}.provider",
                it
            )
            takePictureLauncher.launch(photoUri)
        }
    }

    private val takePictureLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success) {
            binding.imgProfile.setImageURI(photoUri)
        }
    }

    private fun createImageFile(): File? {
        val timeStamp: String =
            SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.getDefault()).format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
        currentPhotoPath = imageFile.absolutePath
        return imageFile
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}