package com.lyh.photoexplorer.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.lyh.photoexplorer.NavHost

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun App(
    appState: AppState = rememberAppState()
) {
    AppTheme {

        Scaffold(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
        ) { padding ->
            Row(
                Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Horizontal
                        )
                    )
            ) {
                NavHost(
                    navController = appState.navController,
                    onBackClick = appState::onBackClick,
                    onNavigateToDestination = appState::navigate,
                    modifier = Modifier
                        .padding(padding)
                        .consumedWindowInsets(padding)
                )
            }
        }
    }
}