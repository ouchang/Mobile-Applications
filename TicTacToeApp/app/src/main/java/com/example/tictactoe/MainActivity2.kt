package com.example.tictactoe

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tictactoe.ui.theme.TicTacToeTheme

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var boardSizeStr = intent.getStringExtra("boardSize").toString()
        var boardSize = boardSizeStr.toInt()

        Log.i("board", "INTENT BOARDSIZE: " + boardSizeStr)

        setContent {
            TicTacToeTheme {
                val viewModel = GameViewModel(boardSize)
                GameScreen(inputBoardSize = boardSize, viewModel = viewModel)
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TicTacToeTheme {
        Greeting("Android")
    }
}