package com.example.practice.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.practice.data.auth.TokenStorage
import com.example.practice.data.repository.UnsplashRepositoryImpl
import com.example.practice.R
import com.example.practice.databinding.FragmentLoginBinding
import com.example.practice.presentation.onboarding.onboarding_utils.hideAppbarAndBottomView
import com.example.practice.presentation.utils.launchAndCollectIn
import com.example.practice.presentation.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by viewModels()
    private val binding by viewBinding(FragmentLoginBinding::bind)

    private val getAuthResponse =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val dataIntent = it.data ?: return@registerForActivityResult
            handleAuthResponseIntent(dataIntent)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideAppbarAndBottomView(requireActivity())
        bindViewModel()
    }

    private fun bindViewModel() {

        binding.buttonLogin.setOnClickListener { viewModel.openLoginPage() }

        viewModel.loadingFlow.launchAndCollectIn(viewLifecycleOwner) {
            updateIsLoading(it)
        }
        viewModel.openAuthPageFlow.launchAndCollectIn(viewLifecycleOwner) {
            openAuthPage(it)
        }
        viewModel.toastFlow.launchAndCollectIn(viewLifecycleOwner) {
            toast(it)
        }
        viewModel.authSuccessFlow.launchAndCollectIn(viewLifecycleOwner) {
            val data = activity?.intent?.data
            val photoIdBundle = Bundle()
            photoIdBundle.putString(PHOTO_ID_KEY, data.toString().substringAfterLast("/"))
            if (data != null) {
                findNavController().navigate(
                    R.id.photoDetailsFragment,
                    photoIdBundle
                )
            } else {
                findNavController().navigate(R.id.photosFragment)
            }

            UnsplashRepositoryImpl.accessToken = "Bearer ${TokenStorage.accessToken}"

            Log.d(
                "Oauth",
                "access: ${TokenStorage.accessToken} id: ${TokenStorage.idToken} refr: ${TokenStorage.refreshToken}"
            )
        }
    }

    private fun updateIsLoading(isLoading: Boolean) = with(binding) {
        buttonLogin.isVisible = !isLoading
        loginProgress.isVisible = isLoading
    }

    private fun openAuthPage(intent: Intent) {
        getAuthResponse.launch(intent)
    }

    private fun handleAuthResponseIntent(intent: Intent) {
        val exception = AuthorizationException.fromIntent(intent)
        val tokenExchangeRequest = AuthorizationResponse.fromIntent(intent)
            ?.createTokenExchangeRequest()
        when {
            exception != null -> viewModel.onAuthCodeFailed(exception)
            tokenExchangeRequest != null ->
                viewModel.onAuthCodeReceived(tokenExchangeRequest)
        }
    }
}