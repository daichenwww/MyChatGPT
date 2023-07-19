package com.example.api_practice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.api_practice.ui.theme.Pink80
import com.example.api_practice.ui.theme.Purple80
import com.example.api_practice.ui.theme.PurpleGrey40

@Composable
fun ChatMessageItem(message: Message) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .background(if (message.role == "user") Pink80 else Purple80)
            .wrapContentWidth(if (message.role == "user") Alignment.End else Alignment.Start), /* Why not work? */

    ) {
        Text(
            text = if (message.role == "user") "You: " else "助理:",
            Modifier.padding(8.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = PurpleGrey40
        )
        Text(
            text = message.content,
            Modifier.padding(8.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )
    }
}

@Composable
fun ImgMessageItem(message: ImgMessage) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .background(if(message.url.startsWith("https://")) Pink80 else Purple80)

    ) {
        Text(
            text = if (message.url.startsWith("https://")) "助理: " else "You:",
            Modifier.padding(8.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = PurpleGrey40
        )
        if (message.url.startsWith("https://")) {
            AsyncImage(
                model = message.url,
                contentDescription = null,
                modifier = Modifier.padding(8.dp)
            )
        }
        else {
            Text(
                text = message.url,
                Modifier.padding(8.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
        }

    }
}

data class Message(val role: String, val content: String)
data class ImgMessage(val url: String)