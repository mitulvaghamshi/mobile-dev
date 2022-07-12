package com.example.animation.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    val colors = lightColors(Purple500, Purple700, Teal200)
    MaterialTheme(colors = colors, content = content)
}
