package com.example.todo.ui.todo

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

/**
 * Styled button for [TodoScreen]
 *
 * @param onClick (event) notify caller of click events
 * @param text button text
 * @param modifier modifier for button
 * @param enabled enable or disable the button
 */
@Composable
fun EditButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) = TextButton(
    onClick = onClick,
    enabled = enabled,
    modifier = modifier,
    shape = CircleShape,
    colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primary)
) { Text(text) }

@Preview
@Composable
private fun PreviewEditButton() {
    EditButton("Submit") {}
}
