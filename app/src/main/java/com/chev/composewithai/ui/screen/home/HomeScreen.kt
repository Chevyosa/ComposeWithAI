package com.chev.composewithai.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chev.composewithai.data.viewmodel.ChatViewModel
import com.chev.composewithai.ui.theme.ComposeWithAITheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: ChatViewModel) {
    val allMessages = viewModel.allMessages
    val isLoading = viewModel.isLoading
    var userMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        textAlign = TextAlign.Center,
                        text = "QwenAIChat",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
            )
        },
        content = { innerPadding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(allMessages) { message ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = if (message.role == "user") Arrangement.End else Arrangement.Start
                        ) {
                            Text(
                                text = message.content,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .background(
                                        if (message.role == "user") Color.LightGray else Color.Transparent,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(12.dp)
                            )
                        }
                    }

                    if (isLoading) {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Text(
                                    text = "Typing...",
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .background(
                                            Color.Transparent,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .padding(12.dp)
                                )
                            }
                        }
                    }
                }

                Row(modifier = Modifier.padding(8.dp)) {
                    TextField(
                        value = userMessage,
                        onValueChange = { userMessage = it },
                        label = { Text("Type a message...") },
                        modifier = Modifier
                            .weight(1f)
                            .shadow(10.dp, RoundedCornerShape(24.dp)),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                        ),
                    )
                    Button(
                        onClick = {
                            if (userMessage.isNotBlank()) {
                                viewModel.sendMessage(userMessage)
                                userMessage = ""
                            }
                        },
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text("Send")
                    }
                }
            }
        }
    )
}


@Preview
@Composable
private fun HomeScreenPreview() {
    ComposeWithAITheme {
        HomeScreen(viewModel = ChatViewModel())
    }
}