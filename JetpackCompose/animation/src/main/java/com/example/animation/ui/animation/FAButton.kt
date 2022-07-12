package com.example.animation.ui.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.animation.R

/**
 * Shows the floating action button.
 *
 * @param extended Whether the tab should be shown in its expanded state.
 */
// AnimatedVisibility is currently an experimental API in Compose Animation.
@Composable
fun FAButton(extended: Boolean, onClick: () -> Unit) {
    // Use `FloatingActionButton` rather than `ExtendedFloatingActionButton` for full control on
    // how it should animate.
    FloatingActionButton(onClick) {
        Row(Modifier.padding(horizontal = if (extended) 20.dp else 16.dp)) {
            Icon(Icons.Default.Edit, null)
            // Toggle the visibility of the content with animation.
            AnimatedVisibility(extended) {
                Text(
                    stringResource(R.string.edit),
                    Modifier.padding(start = 10.dp, top = 3.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewFAButton() {
    FAButton(extended = false) {}
}

@Preview
@Composable
private fun PreviewFAButtonExtended() {
    FAButton(extended = true) {}
}
