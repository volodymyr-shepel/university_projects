package com.rogue.travelguru.ui.presentation.profile.components

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.rogue.travelguru.R
import com.rogue.travelguru.model.DataProvider


@Composable
fun SignedInUserProfileContent(
    pad : PaddingValues,
    signOut : () -> Unit,
    updateProfilePicture : (Uri) -> Unit,
    isSignedInWithGoogle : () -> Boolean,
) {
    var firstName: String by remember {
        mutableStateOf("")
    }
    var lastName: String by remember {
        mutableStateOf("")
    }

    var editFirstName: String by remember {
        mutableStateOf("")
    }
    var editLastName: String by remember {
        mutableStateOf("")
    }

    var picUrl: String by remember {
        mutableStateOf("")
    }
    var editMode by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit) {
        if(isSignedInWithGoogle()){
            firstName = DataProvider.user?.displayName?.split(" ")?.get(0) ?: "Name"
            lastName = DataProvider.user?.displayName?.split(" ")?.get(1) ?: "Surname"
            picUrl = DataProvider.user?.photoUrl.toString()
            editFirstName=firstName
            editLastName=lastName
        }
        else{
            FirebaseFirestore.getInstance().collection("users")
                .document(DataProvider.user?.uid.toString())
                .get()
                .addOnSuccessListener {

                    firstName = it.get("first_name").toString()
                    lastName = it.get("last_name").toString()
                    picUrl = it.get("profile_picture").toString()
                    editFirstName=firstName
                    editLastName=lastName

                }
        }

    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(pad),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.padding(top = 25.dp))
        ProfileImage(Uri.parse(picUrl), isImageModifiable = editMode) {
            updateProfilePicture(it)
        }
        Spacer(modifier = Modifier.padding(vertical = 25.dp))
        OutlinedTextField(
            value = editFirstName, onValueChange = {
                editFirstName = it
            },
            label = { Text(text = stringResource(id = R.string.first_name)) },
            readOnly = !editMode,
            enabled = editMode
        )
        Spacer(modifier = Modifier.padding(vertical = 5.dp))

        OutlinedTextField(
            value = editLastName, onValueChange = {
                editLastName = it
            },
            label = { Text(text = stringResource(id = R.string.last_name)) },
            readOnly = !editMode,
            enabled = editMode

        )
        Spacer(modifier = Modifier.padding(vertical = 25.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {

            if(!isSignedInWithGoogle()){
                Button(
                    onClick = {
                        if (editMode) {
                            FirebaseFirestore.getInstance().collection("users")
                                .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
                                .update(
                                    "first_name", editFirstName,
                                    "last_name", editLastName
                                )
                                .addOnSuccessListener {
                                    firstName = editFirstName
                                    lastName = editLastName
                                }
                                .addOnFailureListener {
                                    editFirstName = firstName
                                    editLastName = lastName
                                }
                        }
                        editMode = !editMode

                    },
                    modifier = Modifier
                        .height(40.dp),
                ) {
                    Text(
                        text = if (!editMode) stringResource(id = R.string.edit_profile) else stringResource(
                            id = R.string.save
                        )
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                    Icon(
                        imageVector = if (!editMode) Icons.Default.Edit else Icons.Default.Save,
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.padding(horizontal = 5.dp))
            }

                Button(
                    onClick = {
                        signOut()
                    },
                    modifier = Modifier
                        .height(40.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.sign_out),
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                    Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null)
                }
        }

    }
}
