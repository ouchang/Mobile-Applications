package com.example.saper

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CompoundButton
import android.widget.GridLayout
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), OnCellClickHandler  {
    lateinit var gridRecyclerView: RecyclerView
    lateinit var mineGridLayoutAdapter: MineGridLayoutAdapter
    lateinit var saperGame: SaperGame
    lateinit var flag: Switch
    lateinit var flagCounter: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        flag = findViewById(R.id.flag) //switch
        flagCounter = findViewById(R.id.flagsLeft)

        gridRecyclerView = findViewById(R.id.activity_main_grid)
        gridRecyclerView.layoutManager = GridLayoutManager(this, 9) // grid 9x9
        saperGame = SaperGame(10) // 10 = numOfBombs
        mineGridLayoutAdapter = MineGridLayoutAdapter(saperGame.getMineGrid().getCells(), this)
        gridRecyclerView.adapter = mineGridLayoutAdapter

        flagCounter.text = String.format("Bombs left: %03d", saperGame.getBombsCounter() - saperGame.getNumOfFlags())
    }

    override fun onCellClick(cell: Cell) {
        //Toast.makeText(this, "Cell was clicked!", Toast.LENGTH_SHORT).show()
        saperGame.handleCellClick(cell)

        flagCounter.text = String.format("Bombs left: %03d", saperGame.getBombsCounter() - saperGame.getNumOfFlags())

        if(saperGame.isGameOver()) {
            Toast.makeText(this, "Game Over!", Toast.LENGTH_SHORT).show()
            saperGame.getMineGrid().revealAllBombs()
        }

        if(saperGame.isGameWon()) {
            Toast.makeText(this, "You Won! Congrats!", Toast.LENGTH_SHORT).show()
            saperGame.getMineGrid().revealAllBombs()
        }

        mineGridLayoutAdapter.setCells(saperGame.getMineGrid().getCells())
    }

    fun restartGame(view: View) {
        saperGame = SaperGame(10)
        mineGridLayoutAdapter.setCells(saperGame.getMineGrid().getCells())
        flagCounter.text = String.format("Bombs left: %03d", saperGame.getBombsCounter() - saperGame.getNumOfFlags())
    }

    fun turnOnFlagMode(view: View) {
        saperGame.changeMode()
        if(flag.isChecked) {
            var border: GradientDrawable = GradientDrawable()
            border.setColor(Color.BLACK)
            border.setStroke(1, Color.MAGENTA) //
        } else {
            var border: GradientDrawable = GradientDrawable()
            border.setColor(Color.BLACK)
        }
    }
}