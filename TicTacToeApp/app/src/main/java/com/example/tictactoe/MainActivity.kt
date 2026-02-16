package com.example.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.tictactoe.ui.theme.*

class MainActivity : ComponentActivity() {
    var boardSize = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeTheme {
                val viewModel = GameViewModel(boardSize)
                GameScreen(inputBoardSize = boardSize, viewModel = viewModel)
            }
        }
    }
}