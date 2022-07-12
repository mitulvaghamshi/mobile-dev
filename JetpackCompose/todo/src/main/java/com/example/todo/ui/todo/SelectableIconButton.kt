package com.example.todo.ui.todo

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.data.TodoIcon

/**
 * Displays a single icon that can be selected.
 *
 * @param icon the icon to draw
 * @param onIconSelected (event) request this icon be selected
 * @param isSelected (state) selection state
 * @param modifier modifier for this element
 */
@Composable
fun SelectableIconButton(
    icon: ImageVector,
    @StringRes contentDescription: Int,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onIconSelected: () -> Unit
) {
    val tint = if (isSelected) {
        MaterialTheme.colors.primary
    } else {
        MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
    }
    TextButton({ onIconSelected() }, modifier, shape = CircleShape) {
        Column {
            Icon(icon, stringResource(contentDescription), tint = tint)
            if (isSelected) {
                Box(
                    Modifier
                        .padding(top = 3.dp)
                        .width(icon.defaultWidth)
                        .height(1.dp)
                        .background(tint)
                )
            } else {
                Spacer(Modifier.height(4.dp))
            }
        }
    }
}

@Preview
@Composable
private fun PreviewSelectableIconButton() {
    val icon = remember { TodoIcon.Default }
    Row {
        SelectableIconButton(icon.imageVector, icon.contentDescription, true) {}
        SelectableIconButton(icon.imageVector, icon.contentDescription, false) {}
    }
}
