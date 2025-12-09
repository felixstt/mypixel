package com.pixel.mypixel.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pixel.mypixel.ui.components.FloatingBottomBar
import com.pixel.mypixel.ui.components.HomeTopBar
import com.pixel.mypixel.ui.screens.GeminiScreen
import com.pixel.mypixel.ui.screens.HomeScreen
import com.pixel.mypixel.ui.screens.TutorialScreen
import com.pixel.mypixel.ui.theme.MyPixelTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPixelApp() {
    val navController = rememberNavController()
    
    // We can track the current route to show/hide the top bar or change FAB
    // For simplicity efficiently, we will just render the structure provided in the prompt.

    Scaffold(
        topBar = {
            // Only visible on Home Tab as per prompt
            HomeTopBar(navController = navController)
        },
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = { /* Global Search Action */ },
            ) {
                Icon(Icons.Default.Search, contentDescription = "Search")
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            FloatingBottomBar(navController = navController)
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(navController = navController, startDestination = "home") {
                composable("home") { HomeScreen() }
                composable("tutorial") { TutorialScreen() }
                composable("gemini") { GeminiScreen() }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    MyPixelTheme {
        MyPixelApp()
    }
}
