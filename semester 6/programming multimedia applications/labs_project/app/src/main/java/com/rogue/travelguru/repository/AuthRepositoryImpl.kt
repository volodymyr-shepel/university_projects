package com.rogue.travelguru.repository

import android.util.Log

import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.rogue.travelguru.core.Constants
import com.rogue.travelguru.model.DataProvider
import com.rogue.travelguru.model.FirebaseSignInResponse
import com.rogue.travelguru.model.FirebaseSignUpResponse
import com.rogue.travelguru.model.OneTapSignInResponse
import com.rogue.travelguru.model.Response
import com.rogue.travelguru.model.SendEmailVerificationResponse
import com.rogue.travelguru.model.SendPasswordResetEmailResponse
import com.rogue.travelguru.model.SignOutResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private var oneTapClient: SignInClient,
    private var googleSignInClient: GoogleSignInClient,

    @Named(Constants.SIGN_IN_REQUEST)
    private var signInRequest: BeginSignInRequest,
    @Named(Constants.SIGN_UP_REQUEST)
    private var signUpRequest: BeginSignInRequest,
): AuthRepository {
    override fun getAuthState(viewModelScope: CoroutineScope) = callbackFlow {
        val authStateListener = AuthStateListener { auth ->
            auth.currentUser?.let { user ->
                /*try {
                    userRepository.getUserDocument(user)
                }
                catch (e: FirestoreException) {
                    if (e.message == "DocumentDoesNotExist") {
                        Log.i(TAG, "User Document Does Not Exist!")
                        GlobalScope.launch(Dispatchers.IO) {
                            verifyAuthTokenResult()
                        }
                    }
                }
                catch(e: Exception) {
                    // other errors
                }*/
            }
            trySend(auth.currentUser)
            Log.i(TAG, "User: ${auth.currentUser?.uid ?: "Not authenticated"}")
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), auth.currentUser)

    override suspend fun signInAnonymously(): FirebaseSignInResponse {
        return try {
            val authResult = auth.signInAnonymously().await()
            authResult?.user?.let { user ->
                Log.i(TAG, "FirebaseAuthSuccess: Anonymous UID: ${user.uid}")
            }
            Response.Success(authResult)
        } catch (error: Exception) {
            Log.e(TAG, "FirebaseAuthError: Failed to Sign in anonymously")
            Response.Failure(error)
        }
    }


    override suspend fun signOut(): SignOutResponse {
        return try {
            oneTapClient.signOut().await()
            auth.signOut()
            Response.Success(true)
        }
        catch (e: java.lang.Exception) {
            Response.Failure(e)
        }
    }


    private suspend fun authenticateUser(credential: AuthCredential): FirebaseSignInResponse {
        // If we have auth user, link accounts, otherwise sign in.
        return if (auth.currentUser != null) {
            authLink(credential)
        } else {
            authSignIn(credential)
        }
    }


    private suspend fun authSignIn(credential: AuthCredential): FirebaseSignInResponse {
        return try {
            val authResult = auth.signInWithCredential(credential).await()
            Log.i(TAG, "User: ${authResult?.user?.uid}")
            DataProvider.updateAuthState(authResult?.user)
            Response.Success(authResult)
        }
        catch (error: Exception) {
            Response.Failure(error)
        }
    }

    private suspend fun authLink(credential: AuthCredential): FirebaseSignInResponse {
        return try {
            val authResult = auth.currentUser?.linkWithCredential(credential)?.await()
            Log.i(TAG, "User: ${authResult?.user?.uid}")
            DataProvider.updateAuthState(authResult?.user)
            Response.Success(authResult)
        }
        catch (error: Exception) {
            Response.Failure(error)
        }
    }

    override suspend fun onTapSignIn(): OneTapSignInResponse {
        return try {
            val signInResult = oneTapClient.beginSignIn(signInRequest).await()

            Response.Success(signInResult)

        } catch (e: Exception) {
            try {
                val signUpResult = oneTapClient.beginSignIn(signUpRequest).await()
                Response.Success(signUpResult)
            } catch(e: Exception) {
                Response.Failure(e)
            }
        }
    }
    override suspend fun signInWithGoogle(credential: SignInCredential): FirebaseSignInResponse {
        // 1.
        val googleCredential = GoogleAuthProvider
            .getCredential(credential.googleIdToken, null)
        // 2.
        return authenticateUser(googleCredential)
    }

    override suspend fun firebaseSignInWithEmailAndPassword(
        email: String,
        password: String
    ): FirebaseSignInResponse{
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            Log.i(TAG, "User: ${authResult?.user?.uid}")
            DataProvider.updateAuthState(authResult?.user)
            Response.Success(authResult)
        }
        catch (error: Exception) {
            Response.Failure(error)
        }
    }

    override suspend fun firebaseSignUpWithEmailAndPassword(
        email: String,
        password: String
    ): FirebaseSignUpResponse {
        return try{
            val signUpResult = auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                val userId= auth.currentUser?.uid
                val user= mutableMapOf<String,Any>()
                user["first_name"]="";
                user["last_name"]="";
                user["profile_picture"]="";

                FirebaseFirestore.getInstance().collection("users").document(userId!!)
                    .set(user)
                    .addOnSuccessListener {  }
                    .addOnFailureListener{}
            }
                .await()
            Log.i(TAG, "Sign Up result: ${signUpResult?.user?.uid}")
            Response.Success(true)
        }
        catch (error: Exception) {
            Response.Failure(error)
        }
    }

    override suspend fun sendPasswordResetEmail(email: String): SendPasswordResetEmailResponse {
        return try {
            val resetEmailResult = auth.sendPasswordResetEmail(email).await()
            Log.i(TAG, "Reset password")
            Response.Success(true)
        }
        catch (e : Exception){
            Response.Failure(e)
        }
    }
    override suspend fun sendEmailVerification(): SendEmailVerificationResponse {
        return try{
            auth.currentUser?.sendEmailVerification()?.await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun reloadFirebaseUser() = try {
        auth.currentUser?.reload()?.await()
        Log.i(TAG, "Reload user")
        Response.Success(true)
    } catch (e: Exception) {
        Response.Failure(e)
    }


    companion object {
        private const val TAG = "AuthRepository"
    }

}