package com.rogue.travelguru.uiTests

import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.rogue.travelguru.ui.presentation.profile.components.SignedInUserProfileContent
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito


class SignedInUserProfileContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockFirebaseAuth: FirebaseAuth = Mockito.mock(FirebaseAuth::class.java)
    private val mockFirebaseFirestore: FirebaseFirestore = Mockito.mock(FirebaseFirestore::class.java)

    @Test
    fun testUserProfileContentDisplays() {
        composeTestRule.setContent {
                SignedInUserProfileContent(
                    pad = PaddingValues(),
                    signOut = {},
                    updateProfilePicture = {},
                    isSignedInWithGoogle = { true }
                )
        }

        composeTestRule.onNodeWithText("Sign Out").assertIsDisplayed()
        composeTestRule.onNodeWithText("Edit Profile").assertDoesNotExist()
        composeTestRule.onNodeWithText("Save").assertDoesNotExist()
    }

    @Test
    fun testEditProfileFunctionality() {
        composeTestRule.setContent {
            MaterialTheme {
                Surface {
                    SignedInUserProfileContent(
                        pad = PaddingValues(),
                        signOut = {},
                        updateProfilePicture = {},
                        isSignedInWithGoogle = { false }
                    )
                }
            }
        }

        // Toggle edit mode
        composeTestRule.onNodeWithText("Edit Profile").performClick()

        // Assert edit mode
        composeTestRule.onNodeWithText("Save").assertIsDisplayed()
        composeTestRule.onNodeWithText("Edit Profile").assertDoesNotExist()
    }

    @Test
    fun testSignOutFunctionality() {
        var signOutCalled = false

        composeTestRule.setContent {

            SignedInUserProfileContent(
                pad = PaddingValues(),
                signOut = { signOutCalled = true },
                updateProfilePicture = {},
                isSignedInWithGoogle = { true }
            )

        }

        // Perform sign out
        composeTestRule.onNodeWithText("Sign Out").performClick()

        // Assert sign out was called
        assert(signOutCalled)
    }

}