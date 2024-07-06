package com.rogue.travelguru.authLogin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import com.rogue.travelguru.model.DataProvider
import com.rogue.travelguru.model.Response
import com.rogue.travelguru.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository,
    val oneTapClient: SignInClient
): ViewModel() {

    val currentUser = getAuthState()

    init {

        getAuthState()
    }
    private fun getAuthState() = repository.getAuthState(viewModelScope)
    fun signInAnonymously() = CoroutineScope(Dispatchers.IO).launch {
        DataProvider.anonymousSignInResponse = Response.Loading
        DataProvider.anonymousSignInResponse = repository.signInAnonymously()
    }

    fun signOut() = CoroutineScope(Dispatchers.IO).launch {
        DataProvider.signOutResponse = Response.Loading
        DataProvider.signOutResponse = repository.signOut()
    }

    fun oneTapSignIn() = CoroutineScope(Dispatchers.IO).launch {
        DataProvider.oneTapSignInResponse = Response.Loading
        DataProvider.oneTapSignInResponse = repository.onTapSignIn()
    }

    fun signInWithGoogle(credentials: SignInCredential) = CoroutineScope(Dispatchers.IO).launch {
        DataProvider.googleSignInResponse = Response.Loading
        DataProvider.googleSignInResponse = repository.signInWithGoogle(credentials)
    }

    fun signInWithEmailAndPassword(email: String, password: String) = CoroutineScope(Dispatchers.IO).launch {
        DataProvider.emailPasswordSignInResponse = Response.Loading
        DataProvider.emailPasswordSignInResponse = repository.firebaseSignInWithEmailAndPassword(email, password)
    }

    fun reloadUser() = viewModelScope.launch {
        DataProvider.reloadUserResponse = Response.Loading
        DataProvider.reloadUserResponse = repository.reloadFirebaseUser()
    }

    fun isSignedInWithGoogle(): Boolean {

        DataProvider.user?.let {
            for (profile in it.providerData) {
                if (profile.providerId == "google.com") {
                    return true
                }
            }
        }
        return false
    }


}