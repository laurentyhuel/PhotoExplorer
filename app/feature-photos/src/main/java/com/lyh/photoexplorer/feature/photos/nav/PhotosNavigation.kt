package com.lyh.photoexplorer.feature.photos.nav

import android.net.Uri
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.lyh.photoexplorer.feature.core.NavigationDestination
import com.lyh.photoexplorer.feature.photos.detail.PhotoRoute
import com.lyh.photoexplorer.feature.photos.list.PhotoListRoute

object PhotoListDestination : NavigationDestination {
    override val route = "photos_route"
    override val destination = "photos_destination"
}

object PhotoDetailDestination : NavigationDestination {
    const val photoIdArg = "photoId"
    override val route = "photo_route/{$photoIdArg}"
    override val destination = "photo_destination"

    /**
     * Creates destination route for an photoId
     */
    fun createNavigationRoute(photoIdArg: String): String {
        val encodedId = Uri.encode(photoIdArg)
        return "photo_route/$encodedId"
    }

    /**
     * Returns the photoId from a [NavBackStackEntry] after an photo destination navigation call
     */
    fun fromNavArgs(entry: NavBackStackEntry): String {
        val encodedId = entry.arguments!!.getString(photoIdArg)
        return Uri.decode(encodedId.toString())
    }
}

fun NavGraphBuilder.photoGraph(
    navigateToPhoto: (String) -> Unit,
    onBackClick: () -> Unit,
) {

    composable(route = PhotoListDestination.route) {
        PhotoListRoute(onNavigateToPhoto = navigateToPhoto)
    }
    composable(
        route = PhotoDetailDestination.route,
        arguments = listOf(
            navArgument(PhotoDetailDestination.photoIdArg) { type = NavType.StringType }
        )
    ) {
        PhotoRoute(
            onBackClick = onBackClick
        )
    }
}
