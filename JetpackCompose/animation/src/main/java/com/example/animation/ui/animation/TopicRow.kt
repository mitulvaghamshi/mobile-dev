package com.example.animation.ui.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.animation.R

/**
 * Shows a row for one topic.
 *
 * @param topic The topic title.
 * @param expanded Whether the row should be shown expanded with the topic body.
 * @param onClick Called when the row is clicked.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TopicRow(topic: String, expanded: Boolean, onClick: () -> Unit) {
    TopicRowSpacer(expanded)
    Surface(onClick, Modifier.fillMaxWidth(), elevation = 2.dp) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
                // This `Column` animates its size when its content changes.
                .animateContentSize()
        ) {
            Row {
                Icon(Icons.Default.Info, null)
                Spacer(Modifier.width(16.dp))
                Text(topic, style = MaterialTheme.typography.body1)
            }
            if (expanded) {
                Spacer(Modifier.height(8.dp))
                Text(stringResource(R.string.lorem_ipsum), textAlign = TextAlign.Justify)
            }
        }
    }
    TopicRowSpacer(expanded)
}

/**
 * Shows a separator for topics.
 */
@Composable
private fun TopicRowSpacer(visible: Boolean) {
    AnimatedVisibility(visible) {
        Spacer(Modifier.height(8.dp))
    }
}

@Preview
@Composable
private fun PreviewTopicRow() {
    TopicRow("Read Article", false) {}
}

@Preview
@Composable
private fun PreviewTopicRowExpanded() {
    TopicRow("Play Music", true) {}
}
