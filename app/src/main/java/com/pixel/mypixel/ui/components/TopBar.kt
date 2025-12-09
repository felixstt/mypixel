package com.pixel.mypixel.ui.components

import android.content.Intent
import android.provider.Settings
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    if (currentRoute == "home") {
        val context = LocalContext.current
        CenterAlignedTopAppBar(
            title = { Text("My Pixel") },
            actions = {
                ElevatedAssistChip(
                    onClick = {
                        val intent = Intent(Settings.ACTION_MANAGE_ACCOUNTS_SETTINGS)
                        // Verify intent resolves or handle exception, typically safe to launch settings
                        try {
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            // fallback
                            context.startActivity(Intent(Settings.ACTION_SETTINGS))
                        }
                    },
                    label = { Text("User Name") }, // Placeholder for Google Sign-In Name
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Account"
                        )
                    }
                )
            }
        )
    }
}
