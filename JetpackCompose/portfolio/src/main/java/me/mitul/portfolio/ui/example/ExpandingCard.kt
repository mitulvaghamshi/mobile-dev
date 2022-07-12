package me.mitul.portfolio.ui.example

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.mitul.portfolio.R
import me.mitul.portfolio.ui.example.data.Message
import me.mitul.portfolio.ui.theme.AppTheme

@Composable
fun ExpandingCard(modifier: Modifier = Modifier, expanded: Boolean = false, message: Message) {
    var isExpanded by rememberSaveable { mutableStateOf(expanded) }
    ExpandingCard(
        message = message,
        modifier = modifier,
        expanded = isExpanded,
        onExpand = { isExpanded = true },
        onCollapse = { isExpanded = false }
    )
}

@Composable
private fun ExpandingCard(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    message: Message,
    onExpand: () -> Unit,
    onCollapse: () -> Unit
) = Card(modifier.padding(8.dp)) {
    Column(
        Modifier
            .animateContentSize()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        Text(message.author)
        if (expanded) {
            Spacer(Modifier.height(8.dp))
            Text(message.body)
            IconButton(onCollapse, Modifier.fillMaxWidth()) {
                Icon(Icons.Default.ExpandLess, stringResource(R.string.cd_collapse))
            }
        } else {
            IconButton(onExpand, Modifier.fillMaxWidth()) {
                Icon(Icons.Default.ExpandMore, stringResource(R.string.cd_expand))
            }
        }
    }
}

@Preview
@Composable
private fun PreviewCards() = AppTheme {
    val message = Message("Android", stringResource(R.string.lorem_ipsum))
    Column {
        ExpandingCard(message = message)
        ExpandingCard(message = message, expanded = true)
    }
}
