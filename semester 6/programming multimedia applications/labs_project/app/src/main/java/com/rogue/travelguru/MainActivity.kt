package com.rogue.travelguru

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.rogue.travelguru.authLogin.AuthViewModel
import com.rogue.travelguru.model.AuthState
import com.rogue.travelguru.model.DataProvider
import com.rogue.travelguru.navigation.Screen
import com.rogue.travelguru.navigation.TGNavGraph
import com.rogue.travelguru.ui.theme.TravelGuruTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private val authViewModel by viewModels<AuthViewModel>()


    @RequiresApi(Build.VERSION_CODES.S)
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)



        setContent {

            navController = rememberNavController()



            TravelGuruTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TGNavGraph(navController = navController)
                    AuthState()
                }

            }


        }
    }


    @Composable
    private fun AuthState() {

        val currentUser = authViewModel.currentUser.collectAsState().value
        DataProvider.updateAuthState(currentUser)

        when (DataProvider.authState) {
            AuthState.SignedOut -> {
                NavigateToSignInScreen()
            }

            AuthState.Anonymous, AuthState.SignedIn -> {
                NavigateToHomeScreen()
            }

            AuthState.AwaitingEmailVerification -> {
                NavigateToVerifyEmailScreen()
            }
        }
    }

    @Composable
    private fun NavigateToSignInScreen() = navController.navigate(Screen.SignIn.route) {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
    }

    @Composable
    private fun NavigateToHomeScreen() = navController.navigate(Screen.Home.route) {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
    }

    @Composable
    private fun NavigateToVerifyEmailScreen() = navController.navigate(Screen.VerifyEmail.route) {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
    }


}


