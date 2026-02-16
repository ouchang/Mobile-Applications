package com.example.spaceinvaders

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.Display
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.room.Room

class LogInActivity : AppCompatActivity() {
    lateinit var usernameEditText : EditText
    lateinit var passwordEditText : EditText
    lateinit var logInButton : Button
    lateinit var signUpButton : Button
    lateinit var infoTextView : TextView

    lateinit var database : UserDatabase
    lateinit var dao : UserDao

    lateinit var spaceInvadersView: SpaceInvadersView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        database = Room.databaseBuilder(applicationContext, UserDatabase::class.java, "user_table")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

        dao = database.userDao()

        usernameEditText = findViewById(R.id.usernameEditView)
        passwordEditText = findViewById(R.id.passwordEditView)
        logInButton = findViewById(R.id.logInButton)
        signUpButton = findViewById(R.id.signUpButton)
        infoTextView = findViewById(R.id.infoTextView)

        usernameEditText.setOnClickListener {
            usernameEditText.text.clear()
        }

        passwordEditText.setOnClickListener {
            passwordEditText.text.clear()
        }

        logInButton.setOnClickListener {
            if(dao.logIn(usernameEditText.text.toString(), passwordEditText.text.toString())) {
                var intent : Intent = Intent(applicationContext, GameActivity::class.java)
                infoTextView.text = ""
                startActivity(intent)

            } else {
                infoTextView.text = "Sorry! Wrong username or password!"
            }
        }

        signUpButton.setOnClickListener {
            var intent : Intent = Intent(applicationContext, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}