package com.example.todo.ui.todo

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.data.TodoIcon

@Composable
fun ItemInput(
    text: String,
    onTextChange: (String) -> Unit,
    icon: TodoIcon,
    onIconChange: (TodoIcon) -> Unit,
    submit: () -> Unit,
    iconsVisible: Boolean,
    buttonSlot: @Composable () -> Unit
) = Column {
    Row(
        Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
    ) {
        InputText(
            text = text,
            onTextChange = onTextChange,
            onImeAction = submit,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Box(Modifier.align(Alignment.CenterVertically)) { buttonSlot() }
    }
    if (iconsVisible) {
        AnimatedIconRow(icon, Modifier.padding(top = 8.dp), onIconChange = onIconChange)
    } else {
        Spacer(Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewItemInput() {
    ItemInput("Android", {}, TodoIcon.Default, {}, {}, true, {})
}
