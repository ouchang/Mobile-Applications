package com.example.spaceinvaders

import android.content.pm.ActivityInfo
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display

class GameActivity : AppCompatActivity() {
    lateinit var spaceInvadersView: SpaceInvadersView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        var display : Display = windowManager.defaultDisplay
        var size : Point = Point()
        display.getSize(size)

        spaceInvadersView = SpaceInvadersView(this, size.x, size.y)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(spaceInvadersView)
    }

    override fun onResume() {
        super.onResume()
        spaceInvadersView.resume()
    }

    override fun onPause() {
        super.onPause()
        spaceInvadersView.pause()
    }
}