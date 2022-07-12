package com.example.animation.ui.animation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animation.R
import com.example.animation.ui.theme.Amber600

/**
 * Shows the weather.
 *
 * @param onRefresh Called when the refresh icon button is clicked.
 */
@Composable
fun WeatherRow(onRefresh: () -> Unit) {
    Row(
        Modifier
            .heightIn(min = 64.dp)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Amber600)
        )
        Spacer(Modifier.width(16.dp))
        Text(stringResource(R.string.temperature), fontSize = 24.sp)
        Spacer(Modifier.weight(1f))
        IconButton(onRefresh) {
            Icon(Icons.Default.Refresh, stringResource(R.string.refresh))
        }
    }
}

@Preview
@Composable
private fun PreviewWeatherRow() {
    WeatherRow {}
}
