package com.rogue.travelguru.ui.presentation.forgot_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rogue.travelguru.model.DataProvider
import com.rogue.travelguru.model.Response
import com.rogue.travelguru.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {

    fun sendPasswordResetEmail(email: String) = viewModelScope.launch {
        DataProvider.sendPasswordResetEmailResponse = Response.Loading
        DataProvider.sendPasswordResetEmailResponse = repository.sendPasswordResetEmail(email)
    }
}