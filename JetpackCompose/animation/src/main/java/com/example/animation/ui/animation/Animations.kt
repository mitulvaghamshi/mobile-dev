/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.animation.ui.animation

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.animation.R
import com.example.animation.ui.theme.Green300
import com.example.animation.ui.theme.Purple100
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Shows the entire screen.
 */
@Composable
fun Animations() {
    // String resources.
    val allTasks = stringArrayResource(R.array.tasks)
    val allTopics = stringArrayResource(R.array.topics).toList()

    // The currently selected tab.
    var tabPage by remember { mutableStateOf(TabPage.Home) }

    // True if the whether data is currently loading.
    var weatherLoading by remember { mutableStateOf(false) }

    // Holds all the tasks currently shown on the task list.
    val tasks = remember { mutableStateListOf(*allTasks) }

    // Holds the topic that is currently expanded to show its body.
    var expandedTopic by remember { mutableStateOf<String?>(null) }

    // True if the message about the edit feature is shown.
    var editMessageShown by remember { mutableStateOf(false) }

    // Simulates loading weather data. This takes 3 seconds.
    suspend fun loadWeather() {
        if (!weatherLoading) {
            weatherLoading = true
            delay(3000L)
            weatherLoading = false
        }
    }

    // Shows the message about edit feature.
    suspend fun showEditMessage() {
        if (!editMessageShown) {
            editMessageShown = true
            delay(3000L)
            editMessageShown = false
        }
    }

    // Load the weather at the initial composition.
    LaunchedEffect(Unit) { loadWeather() }

    val lazyListState = rememberLazyListState()

    // The background color. The value is changed by the current tab.
    val backgroundColor by animateColorAsState(
        if (tabPage == TabPage.Home) Purple100 else Green300
    )

    // The coroutine scope for event handlers calling suspend functions.
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = { HomeTabBar(backgroundColor, tabPage) { tabPage = it } },
        backgroundColor = backgroundColor,
        floatingActionButton = {
            FAButton(lazyListState.isScrollingUp()) {
                coroutineScope.launch { showEditMessage() }
            }
        }
    ) {
        LazyColumn(contentPadding = PaddingValues(16.dp, 32.dp), state = lazyListState) {
            // Weather
            item { Header(stringResource(R.string.weather)) }
            item { Spacer(Modifier.height(16.dp)) }
            item {
                Surface(Modifier.fillMaxWidth(), elevation = 2.dp) {
                    if (weatherLoading) {
                        LoadingRow()
                    } else {
                        WeatherRow { coroutineScope.launch { loadWeather() } }
                    }
                }
            }
            item { Spacer(Modifier.height(32.dp)) }

            // Topics
            item { Header(stringResource(R.string.topics)) }
            item { Spacer(Modifier.height(16.dp)) }
            items(allTopics) { topic ->
                TopicRow(topic, expandedTopic == topic) {
                    expandedTopic = if (expandedTopic == topic) null else topic
                }
            }
            item { Spacer(Modifier.height(32.dp)) }

            // Tasks
            item { Header(stringResource(R.string.tasks)) }
            item { Spacer(Modifier.height(16.dp)) }
            if (tasks.isEmpty()) {
                item {
                    TextButton({ tasks.clear(); tasks.addAll(allTasks) }) {
                        Text(stringResource(R.string.add_tasks))
                    }
                }
            }
            items(tasks.size) { i ->
                val task = tasks.getOrNull(i)
                if (task != null) {
                    key(task) { TaskRow(task) { tasks.remove(task) } }
                }
            }
        }
        EditMessage(editMessageShown)
    }
}

/**
 * Returns whether the lazy list is currently scrolling up.
 */
@Composable
private fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}

@Preview
@Composable
private fun PreviewAnimations() {
    Animations()
}
