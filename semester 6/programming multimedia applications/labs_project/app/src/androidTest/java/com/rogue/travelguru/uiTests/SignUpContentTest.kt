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
import com.rogue.travelguru.ui.presentation.sign_up.components.SignUpContent
import org.junit.Rule
import org.junit.Test

class SignUpContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testButtonIsDisabledWithInvalidEmail() {
        val emailInput = "invalid-email"
        val passwordInput = "ValidPassword123!"
        val confirmPasswordInput = "ValidPassword123!"

        var emailSent = ""
        var passwordSent = ""

        composeTestRule.setContent {
            SignUpContent(
                modifier = Modifier,
                contentPadding = PaddingValues(16.dp),
                onSignUpSubmitted = { email, password ->
                    emailSent = email
                    passwordSent = password
                }
            )
        }
        // Check if the email field is displayed
        composeTestRule.onNodeWithTag("EmailField").assertIsDisplayed()
        // Enter invalid email, valid password and confirmation
        composeTestRule.onNodeWithTag("EmailField").performTextInput(emailInput)
        composeTestRule.onNodeWithTag("PasswordField").performTextInput(passwordInput)
        composeTestRule.onNodeWithTag("ConfirmPasswordField").performTextInput(confirmPasswordInput)
        // Check if the button is not enabled
        composeTestRule.onNodeWithTag("SignUpButton").assertIsNotEnabled()
    }

    @Test
    fun testButtonIsEnabledWithValidFields() {
        val emailInput = "test@example.com"
        val passwordInput = "ValidPassword123!"
        val confirmPasswordInput = "ValidPassword123!"
        var emailSent = ""
        var passwordSent = ""

        composeTestRule.setContent {
            SignUpContent(
                modifier = Modifier,
                contentPadding = PaddingValues(16.dp),
                onSignUpSubmitted = { email, password ->
                    emailSent = email
                    passwordSent = password
                }
            )
        }

        // Check if the email field is displayed
        composeTestRule.onNodeWithTag("EmailField").assertIsDisplayed()

        // Enter valid email, password, and confirmation
        composeTestRule.onNodeWithTag("EmailField").performTextInput(emailInput)
        composeTestRule.onNodeWithTag("PasswordField").performTextInput(passwordInput)
        composeTestRule.onNodeWithTag("ConfirmPasswordField").performTextInput(confirmPasswordInput)

        // Check if the button is enabled
        composeTestRule.onNodeWithTag("SignUpButton").assertIsEnabled()

        // Click the button
        composeTestRule.onNodeWithTag("SignUpButton").performClick()

        // Verify the email and password sent are correct
        assert(emailSent == emailInput)
        assert(passwordSent == passwordInput)
    }

    @Test
    fun testButtonIsEnabledWithDifferentPasswords() {
        val emailInput = "test@example.com"
        val passwordInput = "ValidPassword123!"
        val confirmPasswordInput = "AnotherValidPassword123!"
        var emailSent = ""
        var passwordSent = ""

        composeTestRule.setContent {
            SignUpContent(
                modifier = Modifier,
                contentPadding = PaddingValues(16.dp),
                onSignUpSubmitted = { email, password ->
                    emailSent = email
                    passwordSent = password
                }
            )
        }

        // Check if the email field is displayed
        composeTestRule.onNodeWithTag("EmailField").assertIsDisplayed()

        // Enter valid email, password, and confirmation
        composeTestRule.onNodeWithTag("EmailField").performTextInput(emailInput)
        composeTestRule.onNodeWithTag("PasswordField").performTextInput(passwordInput)
        composeTestRule.onNodeWithTag("ConfirmPasswordField").performTextInput(confirmPasswordInput)
        // Check if the button is enabled
        composeTestRule.onNodeWithTag("SignUpButton").assertIsNotEnabled()

    }
}