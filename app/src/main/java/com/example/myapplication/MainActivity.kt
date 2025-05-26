package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.size.Size
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val cards = listOf(
                Card(
                    url = "https://cdn4.tu-tu.ru/static/bdui/Widget_medium.fd6cc4ac6d91c858e032ca88ab6df549f98ce370.png",
                ),
                Card(
                    url = "https://cdn4.tu-tu.ru/static/bdui/hotels_guarantee_medium_x3.a00ce62288fc1a132a849c4129af1926250dede0.png",
                )
            )
            MyApplicationTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Max),
                        ) {
                            cards.forEach {
                                Card(
                                    modifier = Modifier.weight(1f),
                                    card = it
                                )
                            }
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Max),
                        ) {
                            cards.map { it.copy(contentScale = ContentScale.Fit) }
                                .forEach {
                                    Card(
                                        modifier = Modifier.weight(1f),
                                        card = it
                                    )
                                }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Card(
    modifier: Modifier,
    card: Card,
) {
    val (contentScale, url) = card
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Gray),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .size(Size.ORIGINAL)
                .build(),
            onSuccess = {
                Log.i("MYTAG", "Drawable ${it}")
            },
            onError = {
                Log.e("MYTAG", "Error ${it.result.throwable}")
            },
        )
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painter,
            alignment = Alignment.TopCenter,
            contentScale = contentScale,
            contentDescription = null
        )
        Spacer(Modifier.size(16.dp))
        Column {
            Text(text = "$contentScale")
            Button({}) { Text(text = "Button_1") }
        }
    }
}

private data class Card(
    val contentScale: ContentScale = ContentScale.FillWidth,
    val url: String,
)
