package com.rogue.travelguru.ui.presentation.profile

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.rogue.travelguru.authLogin.AuthViewModel
import com.rogue.travelguru.model.AuthState
import com.rogue.travelguru.model.DataProvider
import com.rogue.travelguru.ui.presentation.profile.components.AnonymousUserProfileContent
import com.rogue.travelguru.ui.presentation.profile.components.SignedInUserProfileContent

@Composable
fun ProfileScreenContent (
    navigateToSignInScreen: () -> Unit,
    pad : PaddingValues,
    profileViewModel: ProfileViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val authState = DataProvider.authState

    if (authState == AuthState.SignedIn) {
        SignedInUserProfileContent(
            updateProfilePicture = profileViewModel::updateProfilePicture,
            signOut = authViewModel::signOut,
            isSignedInWithGoogle = authViewModel::isSignedInWithGoogle,
            pad = pad)
    }
    else{
        AnonymousUserProfileContent(
            pad = pad,
            navigateToSignInScreen = navigateToSignInScreen
        )
    }
}


