package me.mitul.portfolio.ui.example

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.mitul.portfolio.ui.theme.Pink80
import me.mitul.portfolio.ui.theme.Purple200
import me.mitul.portfolio.R
import me.mitul.portfolio.ui.example.data.Message
import me.mitul.portfolio.ui.theme.AppTheme

@Composable
fun MessageCard(modifier: Modifier = Modifier, expanded: Boolean = false, message: Message) {
    var isExpanded by rememberSaveable { mutableStateOf(expanded) }
    val surfaceColor: Color by animateColorAsState(
        if (isExpanded) Pink80 else Purple200,
        spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessLow)
    )
    MessageCard(
        modifier = modifier,
        expanded = isExpanded,
        message = message,
        surfaceColor = surfaceColor
    ) { isExpanded = !isExpanded }
}

@Composable
private fun MessageCard(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    message: Message,
    surfaceColor: Color,
    onToggle: () -> Unit
) = Card(modifier.padding(8.dp), elevation = 10.dp) {
    Row(Modifier.padding(8.dp)) {
        Image(
            painterResource(R.mipmap.ic_launcher),
            stringResource(R.string.profile_desc),
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )
        Spacer(Modifier.width(8.dp))
        Column {
            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                Text(message.author, style = MaterialTheme.typography.subtitle2)
                IconButton(onToggle, Modifier.size(20.dp)) {
                    if (expanded) {
                        Icon(Icons.Filled.ExpandLess, stringResource(R.string.cd_collapse))
                    } else {
                        Icon(Icons.Filled.ExpandMore, stringResource(R.string.cd_expand))
                    }
                }
            }
            Spacer(Modifier.height(4.dp))
            Surface(
                Modifier
                    .animateContentSize()
                    .padding(1.dp),
                MaterialTheme.shapes.medium,
                surfaceColor
            ) {
                Text(
                    message.body,
                    Modifier.padding(4.dp),
                    maxLines = if (expanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.body2,
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewCards() = AppTheme {
    val message = Message("Android", stringResource(R.string.lorem_ipsum))
    Column {
        MessageCard(message = message)
        MessageCard(message = message, expanded = true)
    }
}
