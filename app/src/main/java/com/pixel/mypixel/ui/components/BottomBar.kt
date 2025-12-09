package com.pixel.mypixel.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FloatingBottomBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Floating toolbar at the bottom center
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp) // Lift it up a bit
    ) {
        Surface(
            shadowElevation = 8.dp,
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                NavigationChip(
                    selected = currentRoute == "home",
                    onClick = { navController.navigate("home") },
                    label = "Home",
                    icon = { Icon(Icons.Default.Home, contentDescription = null) }
                )
                NavigationChip(
                    selected = currentRoute == "tutorial",
                    onClick = { navController.navigate("tutorial") },
                    label = "Tutorial",
                    icon = { Icon(Icons.Default.MenuBook, contentDescription = null) }
                )
                NavigationChip(
                    selected = currentRoute == "gemini",
                    onClick = { navController.navigate("gemini") },
                    label = "Gemini",
                    icon = { Icon(Icons.Default.Star, contentDescription = null) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationChip(
    selected: Boolean,
    onClick: () -> Unit,
    label: String,
    icon: @Composable () -> Unit
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { if (selected) Text(label) }, // Only show text when selected to be expressive/compact
        leadingIcon = icon,
        modifier = Modifier.padding(horizontal = 4.dp)
    )
}
