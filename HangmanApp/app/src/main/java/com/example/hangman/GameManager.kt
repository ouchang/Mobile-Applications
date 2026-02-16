package com.example.hangman

import kotlin.random.Random

class GameManager(wordArray: Array<String>) {
    private var lettersUsed: String = ""
    private var maxTries: Int = 11
    private var drawable: Int = R.drawable.wisielec00
    private var currentTries: Int = 0
    //private var words: GameWords = GameWords()
    private var words: Array<String> = wordArray
    private lateinit var underscoreWord: String
    private lateinit var wordToGuess: String

    fun startNewGame(): GameState {
        lettersUsed = ""
        currentTries = 0
        drawable = R.drawable.wisielec00

        var randomIdx = Random.nextInt(0, words.size)
        wordToGuess = words[randomIdx]
        generateUnderscores(wordToGuess)
        return getGameStatus()
    }

    fun generateUnderscores(word: String) {
        var stringBuilder = StringBuilder()

        for(c in word) {
            if(c == '/') {
                stringBuilder.append('/')
            } else {
                stringBuilder.append('_')
            }
        }

        underscoreWord = stringBuilder.toString()
    }

    fun play(letter: Char): GameState {
        if(lettersUsed.contains(letter)) {
            return GameState.Running(lettersUsed, underscoreWord, drawable)
        }

        lettersUsed += letter

        var idxs = mutableListOf<Int>()

        for((idx, c) in wordToGuess.withIndex()) {
            if(c.equals(letter, true)) {
                idxs.add(idx)
            }
        }

        var finalUnderscoreWord = "" + underscoreWord

        for(idx in idxs) {
            var stringBuilder: StringBuilder = StringBuilder(finalUnderscoreWord).also { it.setCharAt(idx, letter) } //
            finalUnderscoreWord = stringBuilder.toString()
        }

        if(idxs.isEmpty()) {
            currentTries++
        }

        underscoreWord = finalUnderscoreWord
        return getGameStatus()
    }

    fun getHangman(): Int {
        return when(currentTries) { // switch-case
            0 -> R.drawable.wisielec00
            1 -> R.drawable.wisielec01
            2 -> R.drawable.wisielec02
            3 -> R.drawable.wisielec03
            4 -> R.drawable.wisielec04
            5 -> R.drawable.wisielec05
            6 -> R.drawable.wisielec06
            7 -> R.drawable.wisielec07
            8 -> R.drawable.wisielec08
            9 -> R.drawable.wisielec09
            10 -> R.drawable.wisielec10
            11 -> R.drawable.wisielec11
            else -> R.drawable.wisielec11
        }
    }

    fun getGameStatus(): GameState {
        if(underscoreWord.equals(wordToGuess, true)) {
            return GameState.Won(wordToGuess)
        } else if(currentTries == maxTries) {
            return GameState.Lost(wordToGuess)
        }

        drawable = getHangman()
        return GameState.Running(lettersUsed, underscoreWord, drawable)
    }
}