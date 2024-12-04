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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chev.composewithai.data.viewmodel.ChatViewModel
import com.chev.composewithai.ui.theme.ComposeWithAITheme

@Composable
fun HomeScreen(viewModel: ChatViewModel) {
    val userMessages by remember { mutableStateOf(viewModel.userMessages) }
    val assistantMessages by remember { mutableStateOf(viewModel.assistantMessages) }
    var userMessage by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxWidth()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(userMessages + assistantMessages) { message ->
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
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        value = userMessage,
                        onValueChange = { userMessage = it },
                        modifier = Modifier.weight(1f),
                        label = { Text("Type a message...") }
                    )
                    Button(
                        onClick = {
                            if (userMessage.isNotBlank()) {
                                viewModel.fetchChatResponse("f21f8326bc84de1e25027b67cad1ee7680b9f71349daa6a274f837b2ef6f67b8", userMessage)
                                userMessage = ""  // Reset field input
                            }
                        },
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text("Send")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    ComposeWithAITheme {
        HomeScreen(viewModel = ChatViewModel())
    }
}