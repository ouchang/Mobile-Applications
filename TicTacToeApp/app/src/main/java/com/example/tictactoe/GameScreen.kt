package com.example.tictactoe

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.tictactoe.ui.theme.*

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun GameScreen(inputBoardSize : Int, viewModel: GameViewModel) {

    val state = viewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(pastelBlue)
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Player O: " + viewModel.state.playerCircleCount, fontSize = 16.sp)
            Text(text = "Draw: " + viewModel.state.drawCount, fontSize = 16.sp)
            Text(text = "Player X: " + viewModel.state.playerCrossCount, fontSize = 16.sp)
            BoardSizeMenu()
        }

        Text(
            text = "Tic Tac Toe",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Cursive,
            color = Purple200
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(20.dp)
                )
                .clip(RoundedCornerShape(20.dp))
                .background(tropicalBlue),
            contentAlignment = Alignment.Center
        ) {

            BoardBase(inputBoardSize)
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .aspectRatio(1f),
                columns = GridCells.Fixed(inputBoardSize)
            ) {
                viewModel.boardItems.forEach {(cellNumber, boardCellValue) ->
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null
                                ) {
                                    viewModel.onAction(UserAction.BoardTapped(cellNumber))
                                },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            AnimatedVisibility(
                                visible = viewModel.boardItems[cellNumber] != BoardCellValue.NONE,
                                enter = scaleIn(tween(1000))
                            ) {
                                if(boardCellValue == BoardCellValue.CIRCLE) {
                                    Circle()
                                } else if(boardCellValue == BoardCellValue.CROSS) {
                                    Cross()
                                }
                            }
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AnimatedVisibility(
                    visible = state.hasWon,
                    enter = fadeIn(tween(2000))
                ) {
                    drawWinLine(state = state, boardSize = inputBoardSize)
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = state.hintText,
                fontSize = 24.sp,
                fontStyle = FontStyle.Italic
            )

            Button(
                onClick = {
                          viewModel.onAction(
                              UserAction.PlayAgainButtonClicked
                          )
                },
                shape = RoundedCornerShape(5.dp),
                elevation = ButtonDefaults.elevation(5.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = tropicalBlue,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Play Again", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun BoardSizeMenu() {
    val disabledItem = 1
    val context = LocalContext.current.applicationContext

    val boardSizeArr = ArrayList<String>()
    for (i in 3 until 21 step 1) {
        if(i == 4) {
            var str :  String = "____________"
            boardSizeArr.add(str)
        }
       var str: String = "Size " + i + "x" + i
       boardSizeArr.add(str)
    }




    // state of the menu
    var expanded by remember {
        mutableStateOf(false)
    }

    Box(
        contentAlignment = Alignment.Center
    ) {
        // 3 vertical dots icon
        IconButton(onClick = {
            expanded = true
        }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Open Options"
            )
        }

        // drop down menu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            // adding items
            boardSizeArr.forEachIndexed { itemIndex, itemValue ->
                DropdownMenuItem(
                    onClick = {

                        var boardSizeStr = itemValue.takeLast(2)
                        Log.i("board", "BOARd_SIZE_STR TAKE1: " + boardSizeStr.take(1))

                        if(boardSizeStr.take(1).equals("x")) {
                            Log.i("board", "BOARd_SIZE_STR: " + boardSizeStr)
                            boardSizeStr = boardSizeStr.takeLast(1)
                        }

                        Log.i("board", "BOARd_SIZE_STR2: " + boardSizeStr)

                        var boardSize : Int = boardSizeStr.toInt()

                        Toast.makeText(context, itemValue, Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(context, MainActivity2::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        intent.putExtra("boardSize", boardSize.toString())
                        Log.i("board", "START NEW INTENT")
                        context.startActivity(intent)
                        expanded = false
                    },
                    enabled = (itemIndex != disabledItem)
                ) {
                    Text(itemValue)
                }
            }
        }
    }
}

@Composable
fun Cross() {
    Canvas(
        modifier = Modifier
            .size(60.dp)
            .padding(5.dp)
    ) {
        drawLine(
            color = pastelGreen,
            strokeWidth = 10f,
            cap= StrokeCap.Round,
            start = Offset(x=0f, y=0f),
            end = Offset(x=size.width, y=size.height)
        )

        drawLine(
            color = pastelGreen,
            strokeWidth = 10f,
            cap= StrokeCap.Round,
            start = Offset(x=0f, y=size.height),
            end = Offset(x=size.width, y=0f)
        )
    }
}
@Composable
fun Circle() {
    Canvas(
        modifier = Modifier
            .size(60.dp)
            .padding(5.dp)
    ) {
        drawCircle(
            color = cherub,
            style = Stroke(width = 10f)
        )
    }
}

@Composable
fun drawWinLine(state : GameState, boardSize : Int) {
    var cellStart = state.lineStartCell
    var cellEnd = state.lineEndCell

    var startCol : Int = cellStart % boardSize
    if(startCol == 0) {
        startCol = boardSize
    }

    var startRow : Int = (cellStart / boardSize)+1
    if(cellStart % boardSize == 0) {
        startRow = cellStart / boardSize
    }

    var endCol : Int = cellEnd % boardSize
    if(endCol == 0) {
        endCol = boardSize
    }

    var endRow : Int = (cellEnd/ boardSize)+1
    if(cellEnd % boardSize == 0) {
        endRow = cellEnd / boardSize
    }

    var startX : Float = (startCol.toFloat()*2-1)/(2*boardSize.toFloat())
    var startY : Float = (startRow.toFloat()*2-1)/(2*boardSize.toFloat())
    var endX : Float = (endCol.toFloat()*2-1)/(2*boardSize.toFloat())
    var endY : Float = (endRow.toFloat()*2-1)/(2*boardSize.toFloat())

    Canvas(
        modifier = Modifier.size(300.dp)
    ) {
        drawLine(
            color = Color.Red,
            strokeWidth = 10f,
            cap = StrokeCap.Round,
            start = Offset(x= startX*size.width, y= startY*size.height),
            end = Offset(x=endX*size.width, y=endY*size.height)
        )
    }
}

@Preview
@Composable
fun Prev() {
    GameScreen(
        3,
        viewModel = GameViewModel(inputBoardSize = 3)
    )
}