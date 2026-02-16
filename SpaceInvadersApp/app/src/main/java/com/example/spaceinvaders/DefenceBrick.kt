package com.example.spaceinvaders

import android.graphics.RectF

class DefenceBrick(row : Int, column : Int, shelterNumber : Int, screenX : Int, screenY : Int) {
    var width : Int = screenX / 90
    var height : Int = screenY / 40

    var isVisible : Boolean = true
    var brickPadding : Int = 1

    var shelterPadding : Int = screenX / 9
    var startHeight : Int = screenY - (screenY / 8 * 2)

    lateinit var rect : RectF

    init {
        rect = RectF(
            (column * width + brickPadding + (shelterPadding * shelterNumber) + shelterPadding + shelterPadding * shelterNumber).toFloat(),
            (row * height + brickPadding + startHeight).toFloat(),
            (column * width + width - brickPadding + (shelterPadding * shelterNumber) + shelterPadding + shelterPadding * shelterNumber).toFloat(),
            (row * height + height - brickPadding + startHeight).toFloat()
        )
    }
}