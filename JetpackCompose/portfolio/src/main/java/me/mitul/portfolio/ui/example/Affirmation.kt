package me.mitul.portfolio.ui.example

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.mitul.portfolio.R

data class Affirmation(
    @androidx.annotation.StringRes val stringResourceId: Int,
    @androidx.annotation.DrawableRes val imageResourceId: Int
)

val affirmations = listOf(
    Affirmation(R.string.affirmation1, R.drawable.image1),
    Affirmation(R.string.affirmation2, R.drawable.image2),
    Affirmation(R.string.affirmation3, R.drawable.image3),
    Affirmation(R.string.affirmation4, R.drawable.image4),
    Affirmation(R.string.affirmation5, R.drawable.image5),
    Affirmation(R.string.affirmation6, R.drawable.image6),
    Affirmation(R.string.affirmation7, R.drawable.image7),
    Affirmation(R.string.affirmation8, R.drawable.image8),
    Affirmation(R.string.affirmation9, R.drawable.image9),
    Affirmation(R.string.affirmation10, R.drawable.image10),
)

@Preview(showBackground = true)
@Composable
fun Affirmation() {
    LazyColumn {
        items(affirmations.size) { index ->
            val item = affirmations[index]
            val content = stringResource(item.stringResourceId)
            Column(Modifier.padding(24.dp)) {
                Card(
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth(),
                    elevation = 16.dp,
                ) {
                    Image(
                        painter = painterResource(item.imageResourceId),
                        contentScale = ContentScale.FillWidth,
                        contentDescription = content,
                    )
                }
                Spacer(Modifier.height(10.dp))
                Text(content, fontSize = 24.sp)
            }
        }
    }
}
