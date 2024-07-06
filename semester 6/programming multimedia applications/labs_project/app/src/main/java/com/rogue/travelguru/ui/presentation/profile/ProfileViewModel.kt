package com.rogue.travelguru.ui.presentation.profile

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor()  : ViewModel(){
    fun updateProfilePicture(uri: Uri) {
        val riversRef =
            FirebaseStorage.getInstance().getReference("profile_pictures/${FirebaseAuth.getInstance().currentUser?.uid.toString()}")
        val uploadTask = riversRef.putFile(uri)

        uploadTask.addOnFailureListener {
            Log.i("FAILURE", "UPLOAD FAILED")
        }.addOnSuccessListener { taskSnapshot ->
            Log.i("SUCCESS", "UPLOAD SUCCESSFUL")
            taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                FirebaseFirestore.getInstance().collection("users")
                    .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
                    .update("profile_picture", uri.toString())
            }

        }

    }
}