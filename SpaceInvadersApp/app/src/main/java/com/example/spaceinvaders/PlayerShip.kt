package com.example.spaceinvaders

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.RectF

class PlayerShip(context : Context, screenX : Int, screenY : Int) {
    lateinit var rect : RectF
    lateinit var bitmap : Bitmap

    var length : Float = 0f
    var height : Float = 0f

    var x : Float = 0f // far left of the rectangle which forms ship
    var y : Float = 0f // top coordinate

    var shipSpeed : Float = 0f

    var STOPPED : Int = 0
    var LEFT : Int = 1
    var RIGHT : Int = 2

    var shipMoving = STOPPED

    init {
        rect = RectF()
        length = screenX .toFloat() / 10
        height = screenY.toFloat() / 10

        bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.playership)

        bitmap = Bitmap.createScaledBitmap(
            bitmap,
            length.toInt(),
            height.toInt(),
            false
        )

        shipSpeed = 350f
    }

    fun update(fps : Long) {
        if(shipMoving == LEFT) {
            x -= shipSpeed / fps
        }

        if(shipMoving == RIGHT) {
            x += shipSpeed / fps
        }

        rect.top = y
        rect.bottom = y + height
        rect.left = x
        rect.right = x + length
    }

    fun setMovementState(state : Int) {
        shipMoving = state
    }
}