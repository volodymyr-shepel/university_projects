package com.rogue.travelguru.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.firebase.auth.FirebaseUser
import com.rogue.travelguru.model.Response.Success

enum class AuthState {
    Anonymous,
    AwaitingEmailVerification,
    SignedIn,
    SignedOut;
}

object DataProvider {

    var oneTapSignInResponse by mutableStateOf<OneTapSignInResponse>(Success(null))

    var anonymousSignInResponse by mutableStateOf<FirebaseSignInResponse>(Success(null))

    var googleSignInResponse by mutableStateOf<FirebaseSignInResponse>(Success(null))

    var emailPasswordSignInResponse by mutableStateOf<FirebaseSignInResponse>(Success(null))

    var emailPasswordSignUpResponse by mutableStateOf<FirebaseSignUpResponse>(Success(false))

    var emailVerificationResponse by mutableStateOf<SendEmailVerificationResponse>(Success(false))

    var reloadUserResponse by mutableStateOf<ReloadUserResponse>(Success(false))

    var signOutResponse by mutableStateOf<SignOutResponse>(Success(false))

    var sendPasswordResetEmailResponse by mutableStateOf<SendPasswordResetEmailResponse>(Success(false))

    var openAlertDialog by mutableStateOf("")
    var user by mutableStateOf<FirebaseUser?>(null)



    private var isAuthenticated by mutableStateOf(false)

    private var isAnonymous by mutableStateOf(false)


    var authState by mutableStateOf(AuthState.SignedOut)

    fun updateAuthState(user: FirebaseUser?) {
        this.user = user
        isAuthenticated = user != null
        isAnonymous = user?.isAnonymous ?: false

        authState = if (isAuthenticated) {
            if (isAnonymous) {
                AuthState.Anonymous
            } else if (user?.isEmailVerified == true) {
                AuthState.SignedIn
            } else {
                AuthState.AwaitingEmailVerification
            }
        } else {
            AuthState.SignedOut
        }
    }

}