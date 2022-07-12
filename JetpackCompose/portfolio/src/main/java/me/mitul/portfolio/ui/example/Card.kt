package me.mitul.portfolio.ui.example

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.mitul.portfolio.R

@Preview
@Composable
fun CardWidget() {
    Card(
        elevation = 16.dp,
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        Column(
            Modifier.padding(24.dp),
        ) {
            Card(
                Modifier
                    .size(280.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                elevation = 16.dp,
                shape = CircleShape,
                border = BorderStroke(2.dp, MaterialTheme.colors.primary)
            ) {
                Image(
                    painterResource(R.drawable.picture),
                    contentDescription = "Profile Picture"
                )
            }
            Spacer(Modifier.height(10.dp))
            Text("Software Developer", fontSize = 24.sp)
            Spacer(Modifier.height(10.dp))
            Row {
                Text("Mitul ", fontWeight = FontWeight.Bold, fontSize = 36.sp)
                Text(
                    "Vahamshi",
                    fontWeight = FontWeight.Bold,
                    color = Color.LightGray,
                    fontSize = 36.sp
                )
                Text("_", fontWeight = FontWeight.ExtraBold, color = Color.Red, fontSize = 36.sp)
            }
            Spacer(Modifier.height(10.dp))
            Box(Modifier.width(400.dp)) {
                Text(
                    "I'm always looking for challenges in the software industry because this is the only thing that I can consider as my building blocks for learning something new. I'm always ready to learn new technologies and, its potential to develop something extraordinary.",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Justify
                )
            }
            Spacer(Modifier.height(10.dp))
            Button({}) {
                Image(Icons.Filled.Download, "Download Icon")
                Spacer(Modifier.width(10.dp))
                Text("Download Resume")
            }
            Divider(
                Modifier.padding(10.dp),
                color = MaterialTheme.colors.primary,
                thickness = 1.dp,
            )
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Button(onClick = { /*TODO*/ }) {
                    Image(Icons.Default.Email, contentDescription = "")
                }
                Button(onClick = { /*TODO*/ }) {
                    Image(Icons.Default.Phone, contentDescription = "")
                }
                Button(onClick = { /*TODO*/ }) {
                    Image(Icons.Default.Message, contentDescription = "")
                }
                Button(onClick = { /*TODO*/ }) {
                    Image(Icons.Default.Message, contentDescription = "")
                }
            }
            repeat(5) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(onClick = { /*TODO*/ }) {
                        Text("Language: $it")
                    }
                    Button(onClick = { /*TODO*/ }) {
                        Text("Language: $it.0.0")
                    }
                }
            }
            repeat(5) {
                Card(Modifier.padding(vertical = 16.dp), elevation = 16.dp) {
                    Column(Modifier.padding(10.dp)) {
                        Button(onClick = { /*TODO*/ }) {
                            Text("Android Gallery")
                        }
                        Text("Jetpack Compose/Kotlin")
                        Text("A repository for Jetpack Compose examples and tutorials.")
                    }
                }
            }

        }
    }
}
