package com.rogue.travelguru.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rogue.travelguru.components.bottomNavigationBar.TGBottomNavigationBar
import com.rogue.travelguru.ui.presentation.forgot_password.ForgotPasswordScreen
import com.rogue.travelguru.ui.presentation.home.FavouritesScreen
import com.rogue.travelguru.ui.presentation.home.HomeScreen
import com.rogue.travelguru.ui.presentation.home.SightViewModel
import com.rogue.travelguru.ui.presentation.home.removeFromFavorites
import com.rogue.travelguru.ui.presentation.home.removeFromFavs
import com.rogue.travelguru.ui.presentation.profile.ProfileScreen
import com.rogue.travelguru.ui.presentation.sight.SightScreen
import com.rogue.travelguru.ui.presentation.sight.data.AudioSightViewModel
import com.rogue.travelguru.ui.presentation.sight.data.VideoSightViewModel
import com.rogue.travelguru.ui.presentation.sights_map.SightsMapScreen
import com.rogue.travelguru.ui.presentation.sign_in.SignInScreen
import com.rogue.travelguru.ui.presentation.sign_up.SignUpScreen
import com.rogue.travelguru.ui.presentation.verify_email.VerifyEmailScreen

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun TGNavGraph(navController: NavHostController){
    val sightViewModel: SightViewModel = viewModel(factory = SightViewModel.Factory)
    val videoViewModel: VideoSightViewModel = viewModel(factory = VideoSightViewModel.Factory)
    val audioViewModel: AudioSightViewModel = viewModel(factory = AudioSightViewModel.Factory)
    NavHost(navController, startDestination = Screen.Home.route) {
        composable(
            route = Screen.SignIn.route
        ) {
            SignInScreen(
                navigateToForgotPasswordScreen = {
                    navController.navigate(Screen.ForgotPassword.route)
                },
                navigateToSignUpScreen = {
                    navController.navigate(Screen.SignUp.route)
                }
            )
        }
        composable(
            route = Screen.SignUp.route
        ) {
            SignUpScreen(
                navigateBack = {
                    navController.popBackStack()
                },

            )
        }
        composable(
            route = Screen.ForgotPassword.route
        ) {
            ForgotPasswordScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = Screen.VerifyEmail.route
        ) {
            VerifyEmailScreen(
                navigateToProfileScreen = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            route = Screen.Home.route
        ) {
            HomeScreen(
                bottomBar = { TGBottomNavigationBar(navController) },
                navigateToSightScreen = {
                    sight -> {
                        sightViewModel.setSight(sight)
                        navController.navigate(Screen.Sight.route)
                    }
                },
                sightViewModel = sightViewModel,
            )
        }
        composable(
            route = Screen.Profile.route
        ) {
            ProfileScreen(
                bottomBar = { TGBottomNavigationBar(navController) },
                navigateToSignInScreen = {

                    navController.navigate(Screen.SignIn.route) },
            )
        }
        composable(
            route = Screen.Favourites.route
        ) {
            FavouritesScreen(
                bottomBar = { TGBottomNavigationBar(navController) },
                navigateToSightScreen = {
                    sight -> {
                        sightViewModel.setSight(sight)
                        navController.navigate(Screen.Sight.route)
                    }
                },
                component = { sight -> {
                        removeFromFavorites(sightViewModel, sight.id)
                    }
                },
                func = {
                    sight -> {
                        removeFromFavs(sightViewModel, sight.id)
                    }
                },
                sightViewModel = sightViewModel,
            )
        }
        composable(
            route = Screen.Sight.route
        ) {
            SightScreen(
                navigateBack = {
                    audioViewModel.releaseAudio()
                    videoViewModel.releaseVideo()
                    navController.popBackStack()
                },
                navigateToSightMapsScreen = {
                    navController.popBackStack()
                    navController.navigate(Screen.SightsMap.route)
                },
                sightViewModel = sightViewModel,
                videoViewModel = videoViewModel,
                audioViewModel = audioViewModel
            )
        }
        composable(
            route = Screen.SightsMap.route
        ) {
            SightsMapScreen(
                bottomBar = {
                    sightViewModel.clearCurrentSight()
                    TGBottomNavigationBar(navController)
                },
                sightViewModel = sightViewModel,
                videoViewModel = videoViewModel,
                audioViewModel = audioViewModel
            )
        }
    }
}