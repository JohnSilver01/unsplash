package com.example.practice.presentation

import android.Manifest
import android.app.DownloadManager
import android.content.Context.DOWNLOAD_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.practice.data.auth.TokenStorage
import com.example.practice.models.photo_details.PhotoDetails
import com.example.practice.presentation.onboarding.onboarding_utils.hideAppbarAndBottomView
import com.example.practice.presentation.onboarding.onboarding_utils.showAppbarAndBottomView
import com.example.practice.presentation.utils.toStringWithKNotation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.practice.R
import com.example.practice.databinding.FragmentPhotoDetailsBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class PhotoDetailsFragment : Fragment(R.layout.fragment_photo_details) {
    private val binding by viewBinding(FragmentPhotoDetailsBinding::bind)
    private val viewModel: PhotoDetailsViewModel by viewModels()

    private var photoDetails: PhotoDetails? = null

    private val requestDownloadPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                photoDetails?.let { downloadPhoto(it) }
            } else {
                Snackbar.make(
                    requireView().findViewById(R.id.constraintLayoutPhotoDetails),
                    getString(R.string.permission_is_required_to_download_and_save_the_file),
                    Snackbar.LENGTH_LONG
                )
                    .setAction(getString(R.string.grant_permission)) {
                        photoDetails?.let { it1 -> checkDownloadPermission(it1) }
                    }
                    .show()
            }
        }

    private val requestLocationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                photoDetails?.let { showUserLocation(it) }
            } else {
                Snackbar.make(
                    requireView().findViewById(R.id.constraintLayoutPhotoDetails),
                    getString(R.string.permission_is_required_to_show_location),
                    Snackbar.LENGTH_LONG
                )
                    .setAction(getString(R.string.grant_permission)) {
                        photoDetails?.let { it1 -> checkLocationPermission(it1) }
                    }
                    .show()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val photoId = arguments?.getString(PHOTO_ID_KEY)
        hideAppbarAndBottomView(requireActivity())

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                photoId?.let {
                    viewModel.getPhotoDetails(it)
                    viewModel.photoDetailsStateFlow.collect {
                        photoDetails = it
                        showPhotoDetails(photoDetails)
                    }
                }
            }
        }
    }

    private fun showPhotoDetails(photoDetails: PhotoDetails?) {
        photoDetails?.let {
            with(binding) {
                Glide
                    .with(imageViewPhoto.context)
                    .load(photoDetails.urls.regular)
                    .placeholder(R.drawable.loading_icon)
                    .thumbnail(Glide.with(imageViewPhoto.context).load(R.drawable.loading_icon))
                    .error(R.drawable.error_sing)
                    .into(imageViewPhoto)
                Glide
                    .with(imageViewAvatar.context)
                    .load(photoDetails.user.profileImage.medium)
                    .transform(CircleCrop())
                    .into(imageViewAvatar)
                textViewAuthorName.text = photoDetails.user.name
                textViewUserName.text = photoDetails.user.username
                textViewTotalLikeCount.text = photoDetails.likes.toStringWithKNotation()
                if (photoDetails.likedByUser) {
                    imageViewLikeYesNo.setImageResource(R.drawable.like_yes)
                } else {
                    imageViewLikeYesNo.setImageResource(R.drawable.like_no)
                }
                textViewLocation.text = photoDetails.user.location
                val tagTitlesList = mutableListOf<String>()
                photoDetails.tags.forEach { tag -> tagTitlesList.add(tag.title) }
                if (tagTitlesList.isNotEmpty()) {
                    textViewTags.text = buildString {
                        append("#")
                        append(tagTitlesList.joinToString(" #"))
                    }
                }
                textViewMade.text = photoDetails.exif.make
                textViewModel.text = photoDetails.exif.model
                textViewExposure.text = photoDetails.exif.exposureTime
                textViewAperture.text = photoDetails.exif.aperture
                textViewFocalLength.text = photoDetails.exif.focalLength
                textViewIso.text = photoDetails.exif.iso.toString()
                textViewDownloads.text = photoDetails.downloads.toStringWithKNotation()
                textViewAbout.text = photoDetails.user.bio
                textViewUserName2.text = photoDetails.user.username

                setupListeners(photoDetails)
            }
        }
    }

    private fun setupListeners(photoDetails: PhotoDetails) {
        binding.imageViewSharePhoto.setOnClickListener {
            sharePhoto(photoDetails)
        }
        binding.imageViewLikeYesNo.setOnClickListener {
            setLikeUnlike(photoDetails)
        }

        binding.textViewLocation.setOnClickListener {
            checkLocationPermission(photoDetails)
        }

        binding.imageViewDownloadIcon.setOnClickListener {
            checkDownloadPermission(photoDetails)
        }

        binding.textViewDownloadLabel.setOnClickListener {
            checkDownloadPermission(photoDetails)
        }
    }

    private fun checkDownloadPermission(photoDetails: PhotoDetails) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestDownloadPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        } else {
            downloadPhoto(photoDetails)
        }

//        shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    private fun checkLocationPermission(photoDetails: PhotoDetails) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestLocationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            showUserLocation(photoDetails)
        }
    }

    private fun downloadPhoto(photoDetails: PhotoDetails) {
        val url = photoDetails.links.download
        val accessToken = "Bearer ${TokenStorage.accessToken}"
        val request = DownloadManager.Request(Uri.parse(url))
            .addRequestHeader("Authorization", accessToken)
            .setTitle("Photo ${photoDetails.id}")
            .setDescription("Downloading....")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)
            .setMimeType("image/*")
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                File.separator + photoDetails.id + ".jpg"
            )
        val dm = activity?.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        dm.enqueue(request)
    }

    private fun showUserLocation(photoDetails: PhotoDetails) {
        val geoUri = Uri.parse("geo:0,0?q=${photoDetails.user.location}")
        val mapIntent = Intent(Intent.ACTION_VIEW, geoUri)
        startActivity(mapIntent)
    }

    private fun setLikeUnlike(photoDetails: PhotoDetails) {
        if (!photoDetails.likedByUser) {
            viewModel.likePhoto(photoDetails.id)
            photoDetails.likedByUser = true
            photoDetails.likes++
            binding.imageViewLikeYesNo.setImageResource(R.drawable.like_yes)
            binding.textViewTotalLikeCount.text = photoDetails.likes.toStringWithKNotation()
        } else {
            viewModel.unlikePhoto(photoDetails.id)
            photoDetails.likedByUser = false
            photoDetails.likes--
            binding.imageViewLikeYesNo.setImageResource(R.drawable.like_no)
            binding.textViewTotalLikeCount.text = photoDetails.likes.toStringWithKNotation()
        }
    }

    private fun sharePhoto(photoDetails: PhotoDetails) {
        val photoLink = Uri.parse(photoDetails.links.html)
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "an unsplash photo")
        intent.putExtra(Intent.EXTRA_TEXT, "$photoLink")
        val chosenIntent = Intent.createChooser(intent, "Share photo")
        startActivity(chosenIntent)
    }

    override fun onDestroy() {
        showAppbarAndBottomView(requireActivity())
        super.onDestroy()
    }

}