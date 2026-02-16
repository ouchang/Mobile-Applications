package com.example.chatapp

import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.database.FirebaseListAdapter
import com.firebase.ui.database.FirebaseListOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import java.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity() {
    lateinit var sendButton : FloatingActionButton
    lateinit var listAdapter : FirebaseListAdapter<ChatMessage>


    companion object {
        val SIGN_IN_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // check if user is already signed in
        if(FirebaseAuth.getInstance().currentUser == null) {
            // Sign in / sign up activity
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setIsSmartLockEnabled(false)
                    .build(),
                SIGN_IN_REQUEST_CODE
            )
        } else {
            // User is already signed in; Welcome user
            Toast.makeText(this, "Welcome " + FirebaseAuth.getInstance().currentUser!!.displayName, Toast.LENGTH_SHORT).show()

            // Load chat room contents
            displayChatMessages()
        }




        sendButton = findViewById(R.id.sendButton)

        sendButton.setOnClickListener {
            var input : EditText = findViewById(R.id.inputEditText)

            FirebaseDatabase.getInstance()
                .getReference()
                .push()
                .setValue(ChatMessage(input.text.toString(), FirebaseAuth.getInstance().currentUser!!.displayName!!))

            // Clear input
            input.setText("")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == SIGN_IN_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                Toast.makeText(this, "Successfully signed in. Welcome!", Toast.LENGTH_SHORT).show()
                displayChatMessages()
            } else {
                Toast.makeText(this, "Incorrect data. Please try again later.", Toast.LENGTH_SHORT).show()

                // Close app
                finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //return super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //return super.onOptionsItemSelected(item)

        if(item.itemId == R.menu.main_menu) {
            AuthUI.getInstance().signOut(this)
                .addOnCompleteListener {
                    Toast.makeText(
                        this@MainActivity,
                        "You have been signed out.",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Close activity
                    finish()
                }
        }

        return true
    }

    private fun displayChatMessages() {
        // Configurate message list
        Log.i("msg", "SET CHAT MSG!")
        var listOfMessages : ListView = findViewById(R.id.messageList)

        // Configurate listAdapter
        val query: Query = FirebaseDatabase.getInstance().getReference()
        var options = FirebaseListOptions.Builder<ChatMessage>().setQuery(query, ChatMessage::class.java).setLayout(R.layout.message).build()
        listAdapter = object : FirebaseListAdapter<ChatMessage>(options) {
            override fun populateView(v: View, model: ChatMessage, position: Int) {

                //Get references to the views of list_item.xml
                Log.i("msg", "SHOW USER: " + model.messageUser + " MSG: " + model.messageText)

                var messageText: TextView = v.findViewById(R.id.messageTextView)
                var messageUser: TextView = v.findViewById(R.id.messageUserTextView)
                var messageTime: TextView = v.findViewById(R.id.messageTimeTextView)

                messageText.setText(model.messageText)
                messageUser.setText(model.messageUser)
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.messageTime));
            }
        }

        listOfMessages.adapter = listAdapter
    }

    override fun onStart() {
        super.onStart()
        listAdapter.startListening()
    }


    override fun onStop() {
        super.onStop()
        listAdapter.stopListening()
    }
}