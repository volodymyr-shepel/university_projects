package com.rogue.travelguru.components.bottomNavigationBar

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.rogue.travelguru.navigation.Screen
import com.rogue.travelguru.core.TGIcons

@Composable
fun TGBottomNavigationBar(navController: NavHostController){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val items = listOf(
        Screen.Home,
        Screen.Profile,
        Screen.SightsMap,
        Screen.Favourites,

    )
    val icons = listOf(
        TGIcons.Home,
        TGIcons.Person,
        TGIcons.LocationOn,
        TGIcons.Sight,
    )
    val selectedIcons = listOf(
        TGIcons.Home,
        TGIcons.Person,
        TGIcons.LocationOn,
        TGIcons.Sight,
    )

    TGNavigationBar{
        items.forEachIndexed { index, item ->
            TGNavigationBarItem(
                icon = {
                    Icon(
                        imageVector = icons[index],
                        contentDescription = item.route,
                    )
                },
                selectedIcon = {
                    Icon(
                        imageVector = selectedIcons[index],
                        contentDescription = item.route,
                    )
                },
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        }
    }



}