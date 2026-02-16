package com.example.tictactoe

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class GameViewModel(inputBoardSize : Int) : ViewModel() {
    var state by mutableStateOf(GameState())
    var boardSize = inputBoardSize
    var boardItems : MutableMap<Int, BoardCellValue> = mutableMapOf()

    init {
        for (i in 1 until boardSize*boardSize+1 step 1) {
            boardItems.put(i, BoardCellValue.NONE)
        }
    }


    fun onAction(action: UserAction) {
        when(action) {
            is UserAction.BoardTapped -> {
                addValueToBoard(action.cellNumber, boardSize = boardSize)
            }
            UserAction.PlayAgainButtonClicked -> {
                gameReset()
            }
        }
    }

    private fun gameReset() {
        boardItems.forEach { (i, _) ->
            boardItems[i] = BoardCellValue.NONE
        }

        state = state.copy(
            playerCircleCount = state.playerCircleCount,
            playerCrossCount = state.playerCrossCount,
            drawCount = state.drawCount,
            hintText = "Player O turn",
            currentTurn = BoardCellValue.CIRCLE,
            victoryType = VictoryType.NONE,
            hasWon = false,
            lineStartCell = 0,
            lineEndCell = 0
        )
    }

    private fun addValueToBoard(cellNumber: Int, boardSize: Int) {
        if(boardItems[cellNumber] != BoardCellValue.NONE) {
            return
        }

        if(state.currentTurn == BoardCellValue.CIRCLE) {
            boardItems[cellNumber] = BoardCellValue.CIRCLE

            if(checkForVictory(BoardCellValue.CIRCLE, cellNumber, boardSize = boardSize)) {
                Log.i("board", "CIRCLE WON")
                state = state.copy(
                    playerCircleCount = state.playerCircleCount + 1,
                    hintText = "Player O Won!",
                    currentTurn = BoardCellValue.NONE,
                    victoryType = state.victoryType,
                    hasWon = true
                )
            } else if(hasBoardFull()) {
                state = state.copy(
                    playerCircleCount = state.playerCircleCount,
                    playerCrossCount = state.playerCrossCount,
                    drawCount = state.drawCount + 1,
                    hintText = "Draw!",
                    currentTurn = BoardCellValue.CROSS,
                    victoryType = state.victoryType,
                    hasWon = state.hasWon
                )
            } else {
                state = state.copy(
                    playerCircleCount = state.playerCircleCount,
                    playerCrossCount = state.playerCrossCount,
                    drawCount = state.drawCount,
                    hintText = "Player X turn",
                    currentTurn = BoardCellValue.CROSS,
                    victoryType = state.victoryType,
                    hasWon = state.hasWon
                )
            }

        } else if(state.currentTurn == BoardCellValue.CROSS) {
            boardItems[cellNumber] = BoardCellValue.CROSS

            if(checkForVictory(BoardCellValue.CROSS, cellNumber, boardSize)) {
                Log.i("board", "CROSS WON")
                state = state.copy(
                    playerCrossCount = state.playerCrossCount + 1,
                    hintText = "Player X Won!",
                    currentTurn = BoardCellValue.NONE,
                    victoryType = state.victoryType,
                    hasWon = true
                )
            } else if(hasBoardFull()) {
                state = state.copy(
                    playerCircleCount = state.playerCircleCount,
                    playerCrossCount = state.playerCrossCount,
                    drawCount = state.drawCount + 1,
                    hintText = "Draw!",
                    currentTurn = BoardCellValue.CROSS,
                    victoryType = state.victoryType,
                    hasWon = state.hasWon
                )
            } else {
                state = state.copy(
                    playerCircleCount = state.playerCircleCount,
                    playerCrossCount = state.playerCrossCount,
                    drawCount = state.drawCount,
                    hintText = "Player O turn",
                    currentTurn = BoardCellValue.CIRCLE,
                    victoryType = state.victoryType,
                    hasWon = state.hasWon
                )
            }
        }
    }

    private fun checkForVictory(symbol: BoardCellValue, cellNumber: Int, boardSize: Int): Boolean {
        Log.i("board", "CHECK_FOR_VICTORY")
        var horizontalLowerBound : Int = 0
        var horizontalUpperBound : Int = 0

        if(cellNumber % boardSize == 0) {
            horizontalLowerBound = (cellNumber / boardSize)*boardSize-(boardSize-1)
            horizontalUpperBound = ((cellNumber / boardSize)+1)*boardSize
        } else {
            horizontalLowerBound = (cellNumber / boardSize)*boardSize+1
            horizontalUpperBound = ((cellNumber / boardSize)+1)*boardSize
        }

        var verticalUpperBound : Int = 0
        var verticalLowerBound : Int = 0
        if(cellNumber % boardSize == 0) {
            verticalLowerBound = boardSize
            verticalUpperBound = boardSize*boardSize
        } else {
            verticalLowerBound = cellNumber % boardSize
            verticalUpperBound = verticalLowerBound + (boardSize*(boardSize-1))
        }

        var numOfStepsBackDiagonalDsc = 0
        var tmpCellnumber = cellNumber
        while(tmpCellnumber > boardSize && tmpCellnumber%boardSize != 1) {
            tmpCellnumber -= boardSize+1
            numOfStepsBackDiagonalDsc++
        }

        var numOfStepsFrontDiagonalDsc = 0
        tmpCellnumber = cellNumber
        while(tmpCellnumber <= boardSize*boardSize-boardSize && tmpCellnumber%boardSize != 0) {
            tmpCellnumber += boardSize+1
            numOfStepsFrontDiagonalDsc++
        }

        var numOfStepsFrontDiagonalAsc = 0
        tmpCellnumber = cellNumber
        while(tmpCellnumber <= boardSize*boardSize-boardSize && tmpCellnumber%boardSize != 1) {
            tmpCellnumber -= boardSize+1
            numOfStepsFrontDiagonalAsc++
        }

        var numOfStepsBackDiagonalAsc = 0
        tmpCellnumber = cellNumber
        while(tmpCellnumber > boardSize && tmpCellnumber%boardSize != 0) {
            tmpCellnumber += boardSize+1
            numOfStepsBackDiagonalAsc++
        }
        Log.i("board", "GOT LOWER/UPPER BOUNDS")
        Log.i("board", "cellno: " + cellNumber + " cellno-1: " + boardItems[cellNumber-1] + " cellno-2: " + boardItems[cellNumber-2])
        Log.i("board", "H_Lower: " + horizontalLowerBound + " H_Upper: " + horizontalUpperBound)
        when {
            cellNumber-1 >= horizontalLowerBound  && cellNumber-2 >= horizontalLowerBound && symbol == boardItems[cellNumber-1] && symbol == boardItems[cellNumber-2] -> {
                Log.i("board", "HORIZONTAL_END")
                state = state.copy(
                    victoryType = VictoryType.HORIZONTAL_END,
                    lineStartCell = cellNumber-2,
                    lineEndCell = cellNumber
                )
                return true
            }

            cellNumber-1 >=horizontalLowerBound  && cellNumber+1 <= horizontalUpperBound && boardItems[cellNumber] == boardItems[cellNumber-1] && boardItems[cellNumber] == boardItems[cellNumber+1] -> {
                Log.i("board", "HORIZONTAL_MIDDlE")
                state = state.copy(
                    victoryType = VictoryType.HORIZONTAL_MIDDLE,
                    lineStartCell = cellNumber-1,
                    lineEndCell = cellNumber+1
                )
                return true
            }

            cellNumber+1 <= horizontalUpperBound  && cellNumber+2 <= horizontalUpperBound && boardItems[cellNumber] == boardItems[cellNumber+1] && boardItems[cellNumber+1] == boardItems[cellNumber+2] -> {
                Log.i("board", "HORIZONTAL_START")
                state = state.copy(
                    victoryType = VictoryType.HORIZONTAL_START,
                    lineStartCell = cellNumber,
                    lineEndCell = cellNumber+2
                )
                return true
            }

            cellNumber-boardSize >= verticalLowerBound  && cellNumber-2*boardSize >= verticalLowerBound && boardItems[cellNumber] == boardItems[cellNumber-boardSize] && boardItems[cellNumber] == boardItems[cellNumber-2*boardSize] -> {
                Log.i("board", "VERTICAL_END")
                state = state.copy(
                    victoryType = VictoryType.VERTICAL_END,
                    lineStartCell = cellNumber-2*boardSize,
                    lineEndCell = cellNumber
                )
                return true
            }

            cellNumber+boardSize <= verticalUpperBound  && cellNumber-boardSize >= verticalLowerBound && boardItems[cellNumber] == boardItems[cellNumber+boardSize] && boardItems[cellNumber] == boardItems[cellNumber-boardSize] -> {
                Log.i("board", "VERTICAL_MIDDLE")
                state = state.copy(
                    victoryType = VictoryType.VERTICAL_MIDDLE,
                    lineStartCell = cellNumber-boardSize,
                    lineEndCell = cellNumber+boardSize
                )
                return true
            }

            cellNumber+boardSize <= verticalUpperBound  && cellNumber+2*boardSize <= verticalUpperBound && boardItems[cellNumber] == boardItems[cellNumber+boardSize] && boardItems[cellNumber] == boardItems[cellNumber+2*boardSize] -> {
                Log.i("board", "VERTICAL_START")
                state = state.copy(
                    victoryType = VictoryType.VERTICAL_START,
                    lineStartCell = cellNumber,
                    lineEndCell = cellNumber+2*boardSize
                )
                return true
            }

            1 <= numOfStepsBackDiagonalDsc  && 2 <= numOfStepsBackDiagonalDsc && boardItems[cellNumber-boardSize-1] == boardItems[cellNumber] && boardItems[cellNumber] == boardItems[cellNumber-2*boardSize-2] -> {
                Log.i("board", "DIAGONAL_DSC_END")
                state = state.copy(
                    victoryType = VictoryType.DIAGONAL_DSC_END,
                    lineStartCell = cellNumber-2*boardSize-2,
                    lineEndCell = cellNumber
                )
                return true
            }

            1 <= numOfStepsBackDiagonalDsc  && 1 <= numOfStepsFrontDiagonalDsc && boardItems[cellNumber-boardSize-1] == boardItems[cellNumber] && boardItems[cellNumber] == boardItems[cellNumber+boardSize+1] -> {
                Log.i("board", "DIAGONAL_DSC_MIDDLE")
                state = state.copy(
                    victoryType = VictoryType.DIAGONAL_DSC_MIDDLE,
                    lineStartCell = cellNumber-boardSize-1,
                    lineEndCell = cellNumber+boardSize+1
                )
                return true
            }

            1 <= numOfStepsFrontDiagonalDsc  && 2 <= numOfStepsFrontDiagonalDsc && boardItems[cellNumber+boardSize+1] == boardItems[cellNumber] && boardItems[cellNumber] == boardItems[cellNumber+2*boardSize+2] -> {
                Log.i("board", "DIAGONAL_DSC_START")
                state = state.copy(
                    victoryType = VictoryType.DIAGONAL_DSC_START,
                    lineStartCell = cellNumber,
                    lineEndCell = cellNumber+2*boardSize+2
                )
                return true
            }

            1 <= numOfStepsBackDiagonalAsc  && 2 <= numOfStepsBackDiagonalAsc && boardItems[cellNumber-boardSize+1] == boardItems[cellNumber] && boardItems[cellNumber] == boardItems[cellNumber-2*boardSize+2] -> {
                Log.i("board", "DIAGONAL_ASC_END")
                state = state.copy(
                    victoryType = VictoryType.DIAGONAL_ASC_END,
                    lineStartCell = cellNumber-2*boardSize+2,
                    lineEndCell = cellNumber
                )
                return true
            }

            1 <= numOfStepsBackDiagonalAsc  && 1 <= numOfStepsFrontDiagonalAsc && boardItems[cellNumber-boardSize+1] == boardItems[cellNumber] && boardItems[cellNumber] == boardItems[cellNumber+boardSize-1] -> {
                Log.i("board", "DIAGONAL_ASC_MIDDLE")
                state = state.copy(
                    victoryType = VictoryType.DIAGONAL_ASC_MIDDLE,
                    lineStartCell = cellNumber-boardSize+1,
                    lineEndCell = cellNumber+boardSize-1
                )
                return true
            }

            1 <= numOfStepsFrontDiagonalAsc  && 2 <= numOfStepsFrontDiagonalAsc && boardItems[cellNumber+boardSize-1] == boardItems[cellNumber] && boardItems[cellNumber] == boardItems[cellNumber+2*boardSize-2] -> {
                Log.i("board", "DIAGONAL_ASC_START")
                state = state.copy(
                    victoryType = VictoryType.DIAGONAL_ASC_START,
                    lineStartCell = cellNumber,
                    lineEndCell = cellNumber+2*boardSize-2
                )
                return true
            }

            else -> return false
        }
    }

    private fun hasBoardFull(): Boolean {
        if(boardItems.containsValue(BoardCellValue.NONE)) {
            return false
        }
        return true
    }
}