package com.rogue.travelguru.model

import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.StateFlow

typealias OneTapSignInResponse = Response<BeginSignInResult>
typealias FirebaseSignInResponse = Response<AuthResult>
typealias SignOutResponse = Response<Boolean>
typealias DeleteAccountResponse = Response<Boolean>
typealias AuthStateResponse = StateFlow<FirebaseUser?>
typealias FirebaseSignUpResponse = Response<Boolean>
typealias SendPasswordResetEmailResponse = Response<Boolean>
typealias SendEmailVerificationResponse = Response<Boolean>
typealias ReloadUserResponse = Response<Boolean>

sealed class Response<out T> {
    object Loading: Response<Nothing>()
    data class Success<out T>(val data: T?): Response<T>()
    data class Failure(val e: Exception): Response<Nothing>()
}