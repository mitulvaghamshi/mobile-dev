package com.example.animation.ui.animation

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.Center
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.animation.R
import com.example.animation.ui.theme.Green800
import com.example.animation.ui.theme.Purple100
import com.example.animation.ui.theme.Purple700

enum class TabPage { Home, Work }

/**
 * Shows the bar that holds 2 tabs.
 *
 * @param backgroundColor The background color for the bar.
 * @param tabPage The [TabPage] that is currently selected.
 * @param onTabSelected Called when the tab is switched.
 */
@Composable
fun HomeTabBar(
    backgroundColor: Color,
    tabPage: TabPage,
    onTabSelected: (tabPage: TabPage) -> Unit
) = TabRow(
    selectedTabIndex = tabPage.ordinal,
    backgroundColor = backgroundColor,
    indicator = { tabPositions -> HomeTabIndicator(tabPositions, tabPage) }
) {
    HomeTab(
        icon = Icons.Default.Home,
        title = stringResource(R.string.home),
        onClick = { onTabSelected(TabPage.Home) }
    )
    HomeTab(
        icon = Icons.Default.AccountBox,
        title = stringResource(R.string.work),
        onClick = { onTabSelected(TabPage.Work) }
    )
}

/**
 * Shows an indicator for the tab.
 *
 * @param tabPositions The list of [TabPosition]s from a [TabRow].
 * @param tabPage The [TabPage] that is currently selected.
 */
@Composable
private fun HomeTabIndicator(tabPositions: List<TabPosition>, tabPage: TabPage) {
    val transition = updateTransition(tabPage, label = "Tab indicator")

    val indicatorLeft by transition.animateDp(
        label = "Indicator left",
        transitionSpec = {
            if (TabPage.Home isTransitioningTo TabPage.Work) {
                // Indicator moves to the right.
                // Low stiffness spring for the left edge so it moves slower than the right edge.
                spring(stiffness = Spring.StiffnessVeryLow)
            } else {
                // Indicator moves to the left.
                // Medium stiffness spring for the left edge so it moves faster than the right edge.
                spring(stiffness = Spring.StiffnessMedium)
            }
        }
    ) { page -> tabPositions[page.ordinal].left }

    val indicatorRight by transition.animateDp(
        label = "Indicator right",
        transitionSpec = {
            if (TabPage.Home isTransitioningTo TabPage.Work) {
                // Indicator moves to the right
                // Medium stiffness spring for the right edge so it moves faster than the left edge.
                spring(stiffness = Spring.StiffnessMedium)
            } else {
                // Indicator moves to the left.
                // Low stiffness spring for the right edge so it moves slower than the left edge.
                spring(stiffness = Spring.StiffnessVeryLow)
            }
        }
    ) { page -> tabPositions[page.ordinal].right }

    val color by transition.animateColor(label = "Border color") { page ->
        if (page == TabPage.Home) Purple700 else Green800
    }

    Box(
        Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.BottomStart)
            .offset(x = indicatorLeft)
            .width(indicatorRight - indicatorLeft)
            .padding(4.dp)
            .fillMaxSize()
            .border(BorderStroke(2.dp, color), RoundedCornerShape(4.dp))
    )
}

/**
 * Shows a tab.
 *
 * @param icon The icon to be shown on this tab.
 * @param title The title to be shown on this tab.
 * @param onClick Called when this tab is clicked.
 * @param modifier The [Modifier].
 */
@Composable
private fun HomeTab(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) = Row(
    modifier
        .clickable(onClick = onClick)
        .padding(16.dp), Center, CenterVertically
) {
    Icon(icon, null)
    Spacer(Modifier.width(16.dp))
    Text(title)
}

@Preview
@Composable
private fun PreviewHomeTabBar() {
    HomeTabBar(Purple100, TabPage.Home) {}
}
