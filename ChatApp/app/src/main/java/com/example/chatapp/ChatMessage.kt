package com.example.chatapp

import java.util.*

class ChatMessage(inputMessageText : String="", inputMessageUser : String="") {
    var messageText : String = ""
    var messageUser : String = ""
    var messageTime : Long = 0

    init {
        messageText = inputMessageText
        messageUser = inputMessageUser

        // Initialize to current time
        messageTime = Date().time
    }
}