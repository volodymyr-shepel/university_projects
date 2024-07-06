package com.rogue.travelguru.repository

import com.google.android.gms.auth.api.identity.SignInCredential
import com.rogue.travelguru.model.AuthStateResponse
import com.rogue.travelguru.model.FirebaseSignInResponse
import com.rogue.travelguru.model.FirebaseSignUpResponse
import com.rogue.travelguru.model.OneTapSignInResponse
import com.rogue.travelguru.model.ReloadUserResponse
import com.rogue.travelguru.model.SendEmailVerificationResponse
import com.rogue.travelguru.model.SendPasswordResetEmailResponse
import com.rogue.travelguru.model.SignOutResponse
import kotlinx.coroutines.CoroutineScope

interface AuthRepository {

    fun getAuthState(viewModelScope: CoroutineScope): AuthStateResponse

    suspend fun signInAnonymously(): FirebaseSignInResponse

    suspend fun signOut(): SignOutResponse

    suspend fun onTapSignIn(): OneTapSignInResponse

    suspend fun signInWithGoogle(credential: SignInCredential): FirebaseSignInResponse

    suspend fun firebaseSignInWithEmailAndPassword(email: String, password: String): FirebaseSignInResponse
    suspend fun firebaseSignUpWithEmailAndPassword(email: String, password: String): FirebaseSignUpResponse

    suspend fun sendPasswordResetEmail(email: String): SendPasswordResetEmailResponse

    suspend fun sendEmailVerification(): SendEmailVerificationResponse

    suspend fun reloadFirebaseUser(): ReloadUserResponse


}