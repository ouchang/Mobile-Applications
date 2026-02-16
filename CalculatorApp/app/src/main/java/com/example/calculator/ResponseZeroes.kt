package com.example.calculationsapi

data class ResponseZeroes(
    val expression: String,
    val operation: String,
    val result: List<Int>
)