package com.example.hangman

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children

class GameActivity : AppCompatActivity() {
    private lateinit var gameManager: GameManager
    private lateinit var wordTextView: TextView
    private lateinit var lettersUsedTextView: TextView
    private lateinit var imageView: ImageView
    private lateinit var frogView: ImageView
    private lateinit var lostGameTextView: TextView
    private lateinit var wonGameTextView: TextView
    private lateinit var restartButton: Button
    private lateinit var lettersLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        var wordArray = resources.getStringArray(R.array.wordlist)
        gameManager = GameManager(wordArray)

        imageView = findViewById(R.id.image)
        frogView = findViewById(R.id.frog)
        wordTextView = findViewById(R.id.word)
        lettersUsedTextView = findViewById(R.id.lettersUsed)
        lostGameTextView = findViewById(R.id.lostGame)
        wonGameTextView = findViewById(R.id.wonGame)
        restartButton = findViewById(R.id.restart)
        lettersLayout = findViewById(R.id.lettersLayout)

        lostGameTextView.visibility = View.GONE
        wonGameTextView.visibility = View.GONE

        frogView.visibility = View.GONE

        restartButton.setOnClickListener {
            startNewGame()
        }

        var gameState = gameManager.startNewGame()
        updateGUI(gameState)

        for(l in lettersLayout.children) {
            if(l is Button) {
                l.setOnClickListener {
                    var gameState = gameManager.play(l.text[0])
                    updateGUI(gameState)
                    l.visibility = View.GONE
                }
            }
        }
    }

    fun updateGUI(gameState: GameState) {
        when(gameState) {
            is GameState.Lost -> showGameLost(gameState.wordToGuess)
            is GameState.Won -> showGameWon(gameState.wordToGuess)
            is GameState.Running -> {
                wordTextView.text = gameState.underscoreWord
                lettersUsedTextView.text = "Used letters: " + gameState.lettersUsed
                imageView.setImageDrawable(ContextCompat.getDrawable(this, gameState.drawable))
            }
        }
    }

    fun showGameLost(wordToGuess: String) {
        wordTextView.text = wordToGuess.uppercase()
        lostGameTextView.visibility = View.VISIBLE
        imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.wisielec11))
        frogView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.img_2))
        lettersLayout.visibility = View.GONE
        frogView.visibility = View.VISIBLE
    }

    fun showGameWon(wordToGuess: String) {
        wordTextView.text = wordToGuess.uppercase()
        wonGameTextView.visibility = View.VISIBLE
        frogView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.img_1))
        lettersLayout.visibility = View.GONE
        frogView.visibility = View.VISIBLE
    }

    fun startNewGame()  {
        lostGameTextView.visibility = View.GONE
        wonGameTextView.visibility = View.GONE
        frogView.visibility = View.GONE

        var gameState = gameManager.startNewGame()

        lettersLayout.visibility = View.VISIBLE

        for(l in lettersLayout.children) {
            l.visibility = View.VISIBLE
        }
        updateGUI(gameState)
    }
}