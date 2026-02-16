package com.example.spaceinvaders

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // log in
        var intent: Intent = Intent(applicationContext, LogInActivity::class.java)
        startActivity(intent)
    }
}