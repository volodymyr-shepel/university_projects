package com.rogue.travelguru.ui.presentation.verify_email.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.rogue.travelguru.authLogin.AuthViewModel
import com.rogue.travelguru.model.DataProvider
import com.rogue.travelguru.model.Response
import com.rogue.travelguru.ui.presentation.sign_in.components.AuthLoginProgressIndicator

@Composable
fun ReloadUser(
    navigateToProfileScreen: () -> Unit
) {
    when(val reloadUserResponse = DataProvider.reloadUserResponse) {
        is Response.Loading -> AuthLoginProgressIndicator()
        is Response.Success -> {
            val isUserReloaded = reloadUserResponse.data ?: false
            LaunchedEffect(isUserReloaded) {
                if (isUserReloaded) {
                    navigateToProfileScreen()
                }
            }
        }
        is Response.Failure -> reloadUserResponse.apply {
            LaunchedEffect(e) {
                Log.i("Error occured when reloading user :", "${reloadUserResponse.e}")
            }
        }
    }
}