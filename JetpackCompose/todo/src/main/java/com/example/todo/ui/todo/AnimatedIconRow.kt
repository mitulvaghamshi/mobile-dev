/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.todo.ui.todo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.data.TodoIcon

/**
 * Draws a row of [TodoIcon] with visibility changes animated.
 *
 * When not visible, will collapse to 16.dp high by default. You can enlarge this with the passed
 * modifier.
 *
 * @param icon (state) the current selected icon
 * @param onIconChange (event) request the selected icon change
 * @param modifier modifier for this element
 * @param visible (state) if the icon should be shown
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedIconRow(
    icon: TodoIcon,
    modifier: Modifier = Modifier,
    visible: Boolean = true,
    onIconChange: (TodoIcon) -> Unit
) {
    // remember these specs so they don't restart if recomposing during the animation
    // this is required since TweenSpec restarts on interruption
    val enter = remember { fadeIn(TweenSpec(300, easing = FastOutLinearInEasing)) }
    val exit = remember { fadeOut(TweenSpec(100, easing = FastOutSlowInEasing)) }
    Box(modifier.defaultMinSize(minHeight = 16.dp)) {
        AnimatedVisibility(visible, enter = enter, exit = exit) {
            IconRow(icon, onIconChange = onIconChange)
        }
    }
}

/**
 * Displays a row of selectable [TodoIcon]
 *
 * @param icon (state) the current selected icon
 * @param onIconChange (event) request the selected icon change
 * @param modifier modifier for this element
 */
@Composable
fun IconRow(
    icon: TodoIcon,
    modifier: Modifier = Modifier,
    onIconChange: (TodoIcon) -> Unit
) = Row(modifier) {
    for (todoIcon in TodoIcon.values()) {
        SelectableIconButton(
            icon = todoIcon.imageVector,
            contentDescription = todoIcon.contentDescription,
            onIconSelected = { onIconChange(todoIcon) },
            isSelected = todoIcon == icon
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewIconRow() {
    IconRow(TodoIcon.Default) {}
}
