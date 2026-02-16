package com.example.spaceinvaders

import android.graphics.RectF

class Bullet(screenY: Int) {
    var x : Float = 0f
    var y : Float = 0f

    var rect : RectF

    var UP : Int = 0
    var DOWN : Int = 1

    var heading : Int = -1
    var speed : Float = 350f

    var width : Int = 3
    var height : Int = 0

    var isActive : Boolean = false

    init {
        height = screenY / 20
        isActive = false

        rect = RectF()
    }

    fun getImpactPointY() : Float {
        if(heading == DOWN) {
            return y + height
        }

        return y
    }

    fun shoot(startX : Float, startY: Float, direction : Int) : Boolean {
        if(!isActive) {
            x = startX
            y = startY
            heading = direction
            isActive = true
            return true
        }

        return false
    }

    fun update(fps : Long) {
        if(heading == UP) {
            y -= speed / fps
        } else {
            y += speed / fps
        }

        rect.left = x
        rect.right = x + width
        rect.top = y
        rect.bottom = y +height

    }
}