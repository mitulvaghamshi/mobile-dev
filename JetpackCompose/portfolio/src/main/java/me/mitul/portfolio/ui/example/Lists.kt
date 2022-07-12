package me.mitul.portfolio.ui.example

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch

@Preview
@Composable
private fun SimpleColumn() {
    Column {
        repeat(10) {
            Text("Item #$it", style = MaterialTheme.typography.subtitle1)
        }
    }
}

@Preview
@Composable
private fun SimpleList() {
    // We save the scrolling position with this state
    val scrollState = rememberScrollState()
    Column(Modifier.verticalScroll(scrollState)) {
        repeat(10) {
            Text("Item #$it", style = MaterialTheme.typography.subtitle1)
        }
    }
}

@Preview
@Composable
private fun LazyList() {
    // We save the scrolling position with this state
    val scrollState = rememberLazyListState()
    LazyColumn(state = scrollState) {
        items(10) {
            Text("Item #$it", style = MaterialTheme.typography.subtitle1)
        }
    }
}

@Preview
@Composable
private fun ImageList() {
    // We save the scrolling position with this state
    val scrollState = rememberLazyListState()
    LazyColumn(state = scrollState) {
        items(10) {
            ImageListItem(it)
        }
    }
}

@Preview
@Composable
private fun ScrollingList() {
    val listSize = 10
    // We save the scrolling position with this state
    val scrollState = rememberLazyListState()
    // We save the coroutine scope where our animated scroll will be executed
    val coroutineScope = rememberCoroutineScope()
    Column {
        Row {
            Button({
                coroutineScope.launch {
                    // 0 is the first item index
                    scrollState.animateScrollToItem(0)
                }
            }) { Text("Scroll to the top") }
            Button({
                coroutineScope.launch {
                    // listSize - 1 is the last index of the list
                    scrollState.animateScrollToItem(listSize - 1)
                }
            }) { Text("Scroll to the end") }
        }
        LazyColumn(state = scrollState) {
            items(listSize) {
                ImageListItem(it)
            }
        }
    }
}

@Preview
@Composable
private fun ImageListItem(index: Int = 0) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            rememberAsyncImagePainter(
                "https://developer.android.com/images/brand/Android_Robot.png"
            ),
            contentDescription = "Android Logo",
            modifier = Modifier.size(50.dp)
        )
        Spacer(Modifier.width(10.dp))
        Text("Am Android Robot #$index", style = MaterialTheme.typography.subtitle1)
    }
}
