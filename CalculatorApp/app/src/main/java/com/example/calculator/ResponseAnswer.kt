package com.example.calculationsapi

data class ResponseAnswer(
    val expression: String,
    val operation: String,
    val result: String
)