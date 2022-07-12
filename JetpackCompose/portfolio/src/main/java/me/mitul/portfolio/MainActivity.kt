package me.mitul.portfolio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import me.mitul.portfolio.ui.example.data.Message
import me.mitul.portfolio.ui.example.data.SampleData
import me.mitul.portfolio.ui.example.MessageCard
import me.mitul.portfolio.ui.example.StaggeredGrid
import me.mitul.portfolio.ui.theme.AppTheme
import me.mitul.portfolio.ui.theme.Blue
import me.mitul.portfolio.ui.theme.Purple40
import me.mitul.portfolio.ui.theme.Purple80

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OnBoardingScreen {
                Column(Modifier.padding(it)) {
                    StaggeredGridView(SampleData.topics)
                    ConversationList(SampleData.conversationSample)
                }
            }
        }
    }
}

@Composable
private fun OnBoardingScreen(content: @Composable (paddingValues: PaddingValues) -> Unit) {
    var showOnBoarding by rememberSaveable { mutableStateOf(true) }
    if (showOnBoarding) {
        OnBoardingContent { showOnBoarding = false }
    } else {
        AppTheme {
            Scaffold(
                content = content,
                topBar = {
                    TopAppBar(
                        title = { Text("Hello Jetpack") },
                        actions = {
                            IconButton({ }) {
                                Icon(Icons.Filled.Favorite, "Favorite Button")
                            }
                        }
                    )
                }
            )
        }
    }
}

@Composable
private fun OnBoardingContent(onContinue: () -> Unit) {
    Surface {
        Column(Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {
            Text("Welcome to the Jetpack Compose!")
            Button(onContinue, Modifier.padding(vertical = 24.dp)) { Text("Continue") }
        }
    }
}

@Composable
private fun ConversationList(message: List<Message>) {
    LazyColumn {
        items(message) {
            MessageCard(message = it)
        }
    }
}

@Composable
private fun StaggeredGridView(list: List<String>) {
    Row(
        Modifier
            .padding(8.dp)
            .horizontalScroll(rememberScrollState())
    ) {
        StaggeredGrid(rows = 4) {
            for (item in list) {
                Chip(Modifier.padding(8.dp), item)
            }
        }
    }
}

@Composable
private fun Chip(modifier: Modifier = Modifier, text: String) {
    var selected by rememberSaveable { mutableStateOf(false) }
    Card(
        modifier, RoundedCornerShape(8.dp),
        if (selected) Blue else Purple80
    ) {
        Row(
            Modifier
                .padding(10.dp)
                .clickable { selected = !selected },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier
                    .size(20.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Purple40)
            )
            Spacer(Modifier.width(8.dp))
            Text(text)
        }
    }
}
