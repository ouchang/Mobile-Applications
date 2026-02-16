package com.example.tictactoe

sealed class UserAction {
    object PlayAgainButtonClicked : UserAction()
    data class BoardTapped(val cellNumber : Int) : UserAction()
}
