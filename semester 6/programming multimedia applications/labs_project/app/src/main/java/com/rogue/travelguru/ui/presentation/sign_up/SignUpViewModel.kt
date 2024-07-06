package com.rogue.travelguru.ui.presentation.sign_up

import androidx.lifecycle.ViewModel
import com.rogue.travelguru.model.DataProvider
import com.rogue.travelguru.model.Response
import com.rogue.travelguru.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {
    fun signUpWithEmailAndPassword(email: String, password: String) = CoroutineScope(Dispatchers.IO).launch {
        DataProvider.emailPasswordSignUpResponse = Response.Loading
        DataProvider.emailPasswordSignUpResponse = repository.firebaseSignUpWithEmailAndPassword(email, password)
    }

    fun sendEmailVerification() = CoroutineScope(Dispatchers.IO).launch {
        DataProvider.emailVerificationResponse = Response.Loading
        DataProvider.emailVerificationResponse = repository.sendEmailVerification()
    }



}