package com.example.tictactoe.ui.theme

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BoardBase(boardSize : Int) {
    Canvas(
        modifier = Modifier
            .size(300.dp)
            .padding(10.dp)
    ) {

        // vertical lines
        for(i in 1 until boardSize step 1) {
            drawLine(
                color = Color.Gray,
                strokeWidth = 5f,
                cap = StrokeCap.Round,
                start = Offset(x = size.width*i/boardSize, y=0f),
                end = Offset(x = size.width*i/boardSize, y=size.height)
            )
        }

        // horizontal lines
        for(i in 1 until boardSize step 1) {
            drawLine(
                color = Color.Gray,
                strokeWidth = 5f,
                cap = StrokeCap.Round,
                start = Offset(x = 0f, y=size.height*i/boardSize),
                end = Offset(x = size.width, y=size.height*i/boardSize)
            )
        }
    }
}

@Preview
@Composable
fun Preview() {
    BoardBase(5)
}