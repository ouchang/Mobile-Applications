package com.example.spaceinvaders

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.RadialGradient
import android.graphics.RectF
import java.util.Random

class Invader(context : Context, row : Int, column : Int, screenX : Int, screenY : Int) {
    lateinit var rect : RectF

    lateinit var bitmap1 : Bitmap
    lateinit var bitmap2 : Bitmap

    var length : Float =0f
    var height : Float = 0f

    var x : Float = 0f
    var y : Float = 0f

    var shipSpeed : Float = 0f

    var LEFT : Int = 1
    var RIGHT : Int = 2

    var shipMoving : Int = RIGHT

    var isVisible : Boolean = false

    init {
        rect = RectF()

        length = screenX.toFloat() / 20
        height = screenY.toFloat() / 20

        isVisible = true
        var padding : Int = screenX / 25
        x = column * (length + padding)
        y = row * (length + padding/4)

        bitmap1 = BitmapFactory.decodeResource(context.resources, R.drawable.invader1)
        bitmap2 = BitmapFactory.decodeResource(context.resources, R.drawable.invader2)

        bitmap1 = Bitmap.createScaledBitmap(
            bitmap1,
            length.toInt(),
            height.toInt(),
            false
        )

        bitmap2 = Bitmap.createScaledBitmap(
            bitmap2,
            length.toInt(),
            height.toInt(),
            false
        )

        shipSpeed = 40f
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

    fun dropDownAndReverse() {
        if(shipMoving == LEFT) {
         shipMoving = RIGHT
        } else {
            shipMoving = LEFT
        }

        y += height
        shipSpeed *= 1.18f
    }

    fun takeAim(playerShipX : Float, playerShipLength: Float) : Boolean {
        var randomNum : Int = -1
        var random : Random = Random()

        // if player is near
        if((playerShipX + playerShipLength > x && playerShipX + playerShipLength < x + length) || (playerShipX > x && playerShipX < x + length)) {
            randomNum = random.nextInt(150)
            if(randomNum == 0) {
                return true
            }
        }

        // if player isnt near (random firing)
        randomNum = random.nextInt(2000)
        if(randomNum == 0) {
            return true
        }

        return false
    }
}