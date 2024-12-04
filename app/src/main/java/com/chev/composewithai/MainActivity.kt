package com.chev.composewithai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chev.composewithai.data.viewmodel.ChatViewModel
import com.chev.composewithai.ui.screen.home.HomeScreen
import com.chev.composewithai.ui.theme.ComposeWithAITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: ChatViewModel = ViewModelProvider(this)[ChatViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            ComposeWithAITheme {
                HomeScreen(viewModel = viewModel)
            }
        }
    }
}