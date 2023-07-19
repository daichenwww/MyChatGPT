package com.example.api_practice
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.api_practice.ui.theme.PurpleGrey80

@Composable
fun ImgChatApp(viewModel: ChatViewModel) {
    val listState = rememberLazyListState()

    LaunchedEffect(key1 = viewModel.imageMessage.size) {
        listState.animateScrollToItem( if (viewModel.imageMessage.isEmpty()) 0 else viewModel.imageMessage.size-1 )
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Messages List
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .padding(top = 16.dp, start = 8.dp, end = 8.dp, bottom = 8.dp)
        ) {
            items(viewModel.imageMessage) { message ->
                ImgMessageItem(message = message)
            }
        }
        ImgUserInput(viewModel)
    }
}


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ImgUserInput(viewModel: ChatViewModel){
    val keyboardController = LocalSoftwareKeyboardController.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
            .background(PurpleGrey80),
        verticalAlignment = Alignment.CenterVertically

    ) {
        var newMessageText by remember { mutableStateOf("") }
        TextField(
            value = newMessageText,
            onValueChange = { newMessageText = it },
            textStyle = MaterialTheme.typography.bodyMedium,
            placeholder = { Text(
                text = "在這裡輸入..."
            ) },
            modifier = Modifier.weight(1f),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Ascii,
                imeAction = ImeAction.Send
            ),
            keyboardActions = KeyboardActions(
                onSend = {
                    viewModel.sendMessageImg(newMessageText)
                    newMessageText = ""
                    keyboardController?.hide()
                }
            )
        )
        IconButton(
            onClick = {
                viewModel.sendMessageImg(newMessageText)
                newMessageText = ""
                keyboardController?.hide()
            }
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "send_message",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}