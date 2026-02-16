package com.example.saper

val BOMB = -1
val BLANK = 0

class Cell(v: Int) {
    private var isFlagged: Boolean = false
    private var isRevealed: Boolean = false
    private var value: Int = v

    fun getValue(): Int {
        return value
    }

    fun setValue(v: Int) {
        value = v
    }

    fun setFlagged(f: Boolean) {
        isFlagged = f
    }

    fun getFlagged(): Boolean {
        return isFlagged
    }

    fun setRevealed(r: Boolean) {
        isRevealed = r
    }

    fun getRevealed(): Boolean {
        return isRevealed
    }
}