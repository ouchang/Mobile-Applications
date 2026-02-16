package com.example.tictactoe

data class GameState(
    val playerCircleCount : Int = 0,
    val playerCrossCount : Int = 0,
    val drawCount : Int = 0,
    val hintText : String = "Player O turn",
    val currentTurn : BoardCellValue = BoardCellValue.CIRCLE,
    val victoryType : VictoryType =VictoryType.NONE,
    val hasWon : Boolean = false,
    val lineStartCell : Int = 0,
    val lineEndCell : Int =0
) {

}

enum class BoardCellValue {
    CIRCLE,
    CROSS,
    NONE
}

enum class VictoryType {
    HORIZONTAL_START,
    HORIZONTAL_MIDDLE,
    HORIZONTAL_END,
    VERTICAL_START,
    VERTICAL_MIDDLE,
    VERTICAL_END,
    DIAGONAL_DSC_START,
    DIAGONAL_DSC_MIDDLE,
    DIAGONAL_DSC_END,
    DIAGONAL_ASC_START,
    DIAGONAL_ASC_MIDDLE,
    DIAGONAL_ASC_END,
    NONE
}
