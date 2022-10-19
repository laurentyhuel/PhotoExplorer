package com.lyh.photoexplorer.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lyh.photoexplorer.feature.core.NavigationDestination

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController()
): AppState {
    return remember(navController) {
        AppState(navController)
    }
}

@Stable
class AppState(
    val navController: NavHostController,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    fun navigate(destination: NavigationDestination, route: String? = null) {
        navController.navigate(route ?: destination.route)
    }

    fun onBackClick() {
        navController.popBackStack()
    }
}


