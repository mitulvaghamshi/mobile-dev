package me.mitul.portfolio.ui.example

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension

@Preview
@Composable
private fun ConstraintLayoutContent() {
    ConstraintLayout {
        val (button, text) = createRefs()
        Button({ }, Modifier.constrainAs(button) {
            top.linkTo(parent.top, margin = 16.dp)
        }) { Text("Button") }
        Text("Text", Modifier.constrainAs(text) {
            top.linkTo(button.bottom, margin = 16.dp)
        })
    }
}

@Preview
@Composable
private fun DecoupledConstraintLayout() {
    BoxWithConstraints {
        val constraints = if (maxWidth < maxHeight) {
            decoupledConstraints(margin = 16.dp) // Portrait constraints
        } else {
            decoupledConstraints(margin = 32.dp) // Landscape constraints
        }
        ConstraintLayout(constraints) {
            Button({}, Modifier.layoutId("button")) { Text("Button") }
            Text("Text", Modifier.layoutId("text"))
        }
    }
}

private fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val button = createRefFor("button")
        val text = createRefFor("text")
        constrain(button) { top.linkTo(parent.top, margin = margin) }
        constrain(text) { top.linkTo(button.bottom, margin) }
    }
}

@Preview
@Composable
private fun ConstraintLayoutContentExample2() {
    ConstraintLayout {
        val (button1, button2, text) = createRefs()
        Button({}, Modifier.constrainAs(button1) {
            top.linkTo(parent.top, margin = 16.dp)
        }) { Text("Button 1") }
        Text("Text", Modifier.constrainAs(text) {
            top.linkTo(button1.bottom, margin = 16.dp)
            centerAround(button1.end)
        })
        val barrier = createEndBarrier(button1, text)
        Button({}, Modifier.constrainAs(button2) {
            top.linkTo(parent.top, margin = 16.dp)
            start.linkTo(barrier)
        }) { Text("Button 2") }
    }
}

@Preview
@Composable
private fun LargeConstraintLayout() {
    ConstraintLayout {
        val text = createRef()
        val guideline = createGuidelineFromStart(fraction = 0.5f)
        Text(
            "This is a very very very very very very very long text",
            Modifier.constrainAs(text) {
                linkTo(start = guideline, end = parent.end)
                width = Dimension.preferredWrapContent
            }
        )
    }
}

private fun Modifier.firstBaselineToTop(firstBaselineToTop: Dp) = this.then(
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        // Check the composable has a first baseline
        check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
        val firstBaseline = placeable[FirstBaseline]
        // Height of the composable with padding - first baseline
        val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
        val height = placeable.height + placeableY
        layout(placeable.width, height) {
            // Where the composable gets placed
            placeable.placeRelative(0, placeableY)
        }
    }
)

@Preview
@Composable
private fun TextWithPaddingToBaselinePreview() {
    Text("Hi there!", Modifier.firstBaselineToTop(32.dp))
}

@Preview
@Composable
private fun TextWithNormalPaddingPreview() {
    Text("Hi there!", Modifier.padding(top = 32.dp))
}

@Preview
@Composable
private fun IntrinsicSize() {
    Row(Modifier.height(IntrinsicSize.Min)) {
        Text(
            "Hello",
            Modifier
                .weight(1f)
                .padding(start = 4.dp)
                .wrapContentWidth(Alignment.Start)
        )
        Divider(
            Modifier
                .fillMaxHeight()
                .width(1.dp),
            Color.Black
        )
        Text(
            "World",
            Modifier
                .weight(1f)
                .padding(end = 4.dp)
                .wrapContentWidth(Alignment.End),
        )
    }
}

@Preview
@Composable
private fun CustomColumn() {
    Layout({
        Text("One")
        Text("Two")
        Text("Three")
    }, Modifier.wrapContentHeight()) { measurables, constraints ->
        val placeables = measurables.map { it.measure(constraints) }
        var yPosition = 0
        layout(constraints.maxWidth, constraints.maxHeight) {
            placeables.forEach {
                it.placeRelative(0, yPosition)
                yPosition += it.height
            }
        }
    }
}
