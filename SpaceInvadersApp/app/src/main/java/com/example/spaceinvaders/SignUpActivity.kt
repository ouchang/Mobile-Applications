package com.example.spaceinvaders

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.room.Room

class SignUpActivity : AppCompatActivity() {
    lateinit var usernameEditText : EditText
    lateinit var passwordEditText : EditText
    lateinit var createAccountButton : Button
    lateinit var infoTextView : TextView

    lateinit var database : UserDatabase
    lateinit var dao : UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        database = Room.databaseBuilder(applicationContext, UserDatabase::class.java, "user_table")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

        dao = database.userDao()

        usernameEditText = findViewById(R.id.usernameEditView)
        passwordEditText = findViewById(R.id.passwordEditView)
        createAccountButton = findViewById(R.id.createAccountButton)
        infoTextView = findViewById(R.id.infoTextView)

        usernameEditText.setOnClickListener {
            usernameEditText.text.clear()
        }

        passwordEditText.setOnClickListener {
            passwordEditText.text.clear()
        }

        createAccountButton.setOnClickListener {
            if(!dao.isUsernameTaken(usernameEditText.text.toString())) {
                var newUser : User = User(0, usernameEditText.text.toString(), passwordEditText.text.toString())
                dao.insertUser(newUser)
                infoTextView.text = ""
                finish()
            } else {
                infoTextView.text = "This username is already taken!"
            }
        }
    }
}