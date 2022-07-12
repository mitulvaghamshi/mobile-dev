package com.example.animation.ui.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.animation.R
import com.example.animation.ui.theme.AppTheme

/**
 * Shows a message that the edit feature is not available.
 */
@Composable
fun EditMessage(shown: Boolean) {
    AnimatedVisibility(
        visible = shown,
        enter = slideInVertically(
            // Enters by sliding in from offset -fullHeight to 0.
            initialOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(150, easing = LinearOutSlowInEasing)
        ),
        exit = slideOutVertically(
            // Exits by sliding out from offset 0 to -fullHeight.
            targetOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(250, easing = FastOutLinearInEasing)
        )
    ) {
        Surface(Modifier.fillMaxWidth(), color = MaterialTheme.colors.secondary, elevation = 4.dp) {
            Row(Modifier.padding(horizontal = 16.dp), SpaceBetween, CenterVertically) {
                Text(stringResource(R.string.edit_message))
                TextButton({}) { Text("Dismiss") }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewEditMessage() = AppTheme {
    EditMessage(shown = true)
}
