package com.rogue.travelguru.navigation

import androidx.annotation.StringRes
import com.rogue.travelguru.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Home : Screen("home", R.string.home_screen)
    object Profile : Screen("profile", R.string.profile_screen)
    object Sight : Screen("sight", R.string.sight_screen)
    object Favourites : Screen("favourites", R.string.favs_screen)
    object SightsMap : Screen("sights_map", R.string.sights_map_screen)
    object SignIn : Screen("sign_in",R.string.sign_in)
    object SignUp : Screen("sign_up",R.string.sign_up)
    object ForgotPassword : Screen("forgot_password",R.string.forgot_password)
    object VerifyEmail : Screen("verify_email",R.string.verify_email)
}