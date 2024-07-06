package com.rogue.travelguru.uiTests

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.unit.dp
import com.rogue.travelguru.ui.presentation.forgot_password.components.ForgotPasswordContent
import org.junit.Rule
import org.junit.Test

class ForgotPasswordContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testForgotPasswordContent() {
        val emailInput = "test@example.com"
        var emailSent = ""

        composeTestRule.setContent {
            ForgotPasswordContent(
                modifier = Modifier,
                padding = PaddingValues(16.dp),
                sendPasswordResetEmail = { email ->
                    emailSent = email
                }
            )
        }



        composeTestRule.onNodeWithText("Reset").assertIsDisplayed()
        composeTestRule.onNodeWithTag("EmailField").performTextInput(emailInput)

        composeTestRule.onNodeWithTag("ResetPasswordButton").performClick()

        // Verify the email sent is correct
        assert(emailSent == emailInput)
    }

    @Test
    fun testButtonIsDisabledWithInvalidEmail() {
        var emailSent = ""

        composeTestRule.setContent {
            ForgotPasswordContent(
                modifier = Modifier,
                padding = PaddingValues(16.dp),
                sendPasswordResetEmail = { email ->
                    emailSent = email
                }
            )
        }

        // Check if the email field is displayed
        composeTestRule.onNodeWithTag("EmailField").assertIsDisplayed()

        // Enter invalid text into the email field
        composeTestRule.onNodeWithTag("EmailField").performTextInput("invalid-email")

        // Check if the button is not enabled
        composeTestRule.onNodeWithTag("ResetPasswordButton").assertIsNotEnabled()

        // Try clicking the button (should not change emailSent)
        composeTestRule.onNodeWithTag("ResetPasswordButton").performClick()
        assert(emailSent.isEmpty())
    }

    @Test
    fun testButtonIsEnabledWithValidEmail() {
        val emailInput = "test@example.com"
        var emailSent = ""

        composeTestRule.setContent {
            ForgotPasswordContent(
                modifier = Modifier,
                padding = PaddingValues(16.dp),
                sendPasswordResetEmail = { email ->
                    emailSent = email
                }
            )
        }

        // Check if the email field is displayed
        composeTestRule.onNodeWithTag("EmailField").assertIsDisplayed()

        // Enter valid text into the email field
        composeTestRule.onNodeWithTag("EmailField").performTextInput(emailInput)

        // Check if the button is enabled
        composeTestRule.onNodeWithTag("ResetPasswordButton").assertIsEnabled()

        // Click the button
        composeTestRule.onNodeWithTag("ResetPasswordButton").performClick()

        // Verify the email sent is correct
        assert(emailSent == emailInput)
    }

}
