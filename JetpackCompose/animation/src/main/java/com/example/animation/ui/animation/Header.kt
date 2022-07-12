package com.example.animation.ui.animation

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.example.animation.ui.theme.AppTheme

/**
 * Shows the header label.
 *
 * @param title The title to be shown.
 */
@Composable
fun Header(title: String) {
    Text(
        text = title,
        modifier = Modifier.semantics { heading() },
        style = MaterialTheme.typography.h5
    )
}

@Preview
@Composable
private fun PreviewHeader() = AppTheme {
    Header(title = "Hello World!")
}
