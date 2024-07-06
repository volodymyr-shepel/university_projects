package com.rogue.travelguru.uiTests

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import com.rogue.travelguru.model.AuthState
import com.rogue.travelguru.model.DataProvider
import com.rogue.travelguru.ui.presentation.profile.ProfileScreenContent
import com.rogue.travelguru.ui.presentation.profile.components.AnonymousUserProfileContent
import org.junit.Rule
import org.junit.Test

class AnonymousProfileScreenContentTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun anonymousUserProfileContentIsDisplayed_whenUserIsAnonymous() {
        // Set up the auth state to Anonymous
        DataProvider.authState = AuthState.Anonymous

        // Render the composable
        composeTestRule.setContent {
            AnonymousUserProfileContent(
                navigateToSignInScreen = {},
                pad = PaddingValues(16.dp)
            )
        }

        // Verify the AnonymousUserProfileContent is displayed
        composeTestRule.onNodeWithText("Sign In") // Change this text to match your UI elements
            .assertIsDisplayed()
    }

    @Test
    fun navigateToSignInScreen_whenAnonymousUserClicksSignIn() {
        // Set up the auth state to Anonymous
        DataProvider.authState = AuthState.Anonymous

        // Mock navigation function
        var navigateToSignInScreenCalled = false
        val navigateToSignInScreen = { navigateToSignInScreenCalled = true }

        // Render the composable
        composeTestRule.setContent {
            AnonymousUserProfileContent(
                navigateToSignInScreen = navigateToSignInScreen,
                pad = PaddingValues(16.dp)
            )
        }

        // Perform click on "Sign In" button
        composeTestRule.onNodeWithText("Sign In")
            .performClick()

        // Verify the navigation function is called
        assert(navigateToSignInScreenCalled)
    }
}
