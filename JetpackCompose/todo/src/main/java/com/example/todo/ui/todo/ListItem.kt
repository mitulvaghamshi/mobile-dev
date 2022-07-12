package com.example.todo.ui.todo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.data.TodoItem
import com.example.todo.data.generateRandomTodoItem
import com.example.todo.data.randomTint
import com.example.todo.ui.theme.AppTheme

/**
 * Stateless composable that displays a full-width [TodoItem].
 *
 * @param todo item to show
 * @param onItemClicked (event) notify caller that the row was clicked
 * @param modifier modifier for this element
 */
@Composable
fun ListItem(
    todo: TodoItem,
    modifier: Modifier = Modifier,
    iconAlpha: Float = remember(todo.id) { randomTint() },
    onItemClicked: (TodoItem) -> Unit
) = Row(modifier
    .clickable { onItemClicked(todo) }
    .padding(horizontal = 16.dp, vertical = 8.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    content = {
        Text(todo.task)
        Icon(
            imageVector = todo.icon.imageVector,
            tint = LocalContentColor.current.copy(iconAlpha),
            contentDescription = stringResource(todo.icon.contentDescription)
        )
    }
)

@Preview(showBackground = true)
@Composable
private fun PreviewTodoRow() = AppTheme {
    val todo = remember { generateRandomTodoItem() }
    ListItem(todo, Modifier.fillMaxWidth()) {}
}
