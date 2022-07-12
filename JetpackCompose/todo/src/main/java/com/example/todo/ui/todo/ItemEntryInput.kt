package com.example.todo.ui.todo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.example.todo.data.TodoIcon
import com.example.todo.data.TodoItem

@Composable
fun ItemEntryInput(onItemComplete: (TodoItem) -> Unit) {
    val (text, setText) = remember { mutableStateOf("") }
    val (icon, setIcon) = remember { mutableStateOf(TodoIcon.Default) }
    val iconsVisible = text.isNotBlank()
    val submit = {
        onItemComplete(TodoItem(text, icon))
        setIcon(TodoIcon.Default)
        setText("")
    }
    ItemInput(
        text = text,
        onTextChange = setText,
        icon = icon,
        onIconChange = setIcon,
        submit = submit,
        iconsVisible = iconsVisible,
        buttonSlot = {
            EditButton(
                onClick = submit,
                enabled = text.isNotBlank(),
                text = "Add"
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewItemEntryInput() {
    ItemEntryInput {}
}
