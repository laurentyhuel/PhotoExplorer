package com.lyh.photoexplorer

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.lyh.photoexplorer.feature.core.NavigationDestination
import com.lyh.photoexplorer.feature.photos.nav.PhotoDetailDestination
import com.lyh.photoexplorer.feature.photos.nav.PhotoListDestination
import com.lyh.photoexplorer.feature.photos.nav.photoGraph

@Composable
fun NavHost(
    navController: NavHostController,
    onNavigateToDestination: (NavigationDestination, String) -> Unit,
    startDestination: String = PhotoListDestination.route,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    androidx.navigation.compose.NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        photoGraph(
            navigateToPhoto = {
                onNavigateToDestination(
                    PhotoDetailDestination, PhotoDetailDestination.createNavigationRoute(it)
                )
            },
            onBackClick = onBackClick
        )
    }
}