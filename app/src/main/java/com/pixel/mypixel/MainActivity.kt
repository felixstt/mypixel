package com.pixel.mypixel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.pixel.mypixel.ui.MyPixelApp
import com.pixel.mypixel.ui.theme.MyPixelTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyPixelTheme {
                MyPixelApp()
            }
        }
    }
}
