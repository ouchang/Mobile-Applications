package com.example.saper

import android.text.BoringLayout
import android.util.Log

class SaperGame {
    private var mineGrid: MineGrid = MineGrid()
    private var clearMode: Boolean = true
    private var isGameOver: Boolean = false
    private var flagMode: Boolean = false
    private var numOfFlags: Int = 0
    private var bombsCounter: Int = 0

    constructor(numOfBombs : Int) {
        bombsCounter = numOfBombs
        mineGrid.generateGrid(numOfBombs)
    }

    fun getMineGrid(): MineGrid {
        return mineGrid
    }

    fun handleCellClick(cell: Cell) {
        if(!isGameOver && !isGameWon() && !cell.getRevealed()) {
            if(clearMode) {
                clear(cell)
            } else if(flagMode) {
                flagCell(cell)
            }
        }
    }

    fun clear(cell: Cell) {
        var idx: Int = getMineGrid().getCells().indexOf(cell)
        getMineGrid().getCells().get(idx).setRevealed(true)

        if(cell.getValue() == BLANK) {
            var toClear: ArrayList<Cell> = ArrayList<Cell>()
            var toCheckAdjacents: ArrayList<Cell> = ArrayList<Cell>()

            toCheckAdjacents.add(cell)

            while(toCheckAdjacents.size > 0) {
                var c: Cell = toCheckAdjacents[0]
                var cellIdx: Int = getMineGrid().getCells().indexOf(c)
                var cellPositions: IntArray = getMineGrid().toXY(cellIdx)

                for(a in getMineGrid().getAdjacentCells(cellPositions[0], cellPositions[1])) {
                    if(a.getValue() == BLANK) {
                        if(!toClear.contains(a)) {
                            if(!toCheckAdjacents.contains(a)) {
                                toCheckAdjacents.add(a)
                            }
                        }
                    } else {
                        if (!toClear.contains(a)) {
                            toClear.add(a)
                        }
                    }
                }

                toCheckAdjacents.remove(c)
                toClear.add(c)
            }

            for(c in toClear) {
                c.setRevealed(true)
            }
        } else if(cell.getValue() == BOMB) {
            isGameOver = true
        }
    }

    fun changeMode() {
        clearMode = !clearMode
        flagMode = !flagMode
    }

    fun isClearMode(): Boolean {
        return clearMode
    }

    fun isFlagMode(): Boolean {
        return flagMode
    }

    fun getNumOfFlags(): Int {
        return numOfFlags
    }

    fun getBombsCounter(): Int {
        return bombsCounter
    }

    fun flagCell(cell: Cell) {
        if(!cell.getRevealed()) {
            cell.setFlagged(!cell.getFlagged())
            var counter: Int = 0
            for (c in getMineGrid().getCells()) {
                if (c.getFlagged()) {
                    counter++
                }
            }
            numOfFlags = counter
        }
    }

    fun isGameOver(): Boolean {
        return isGameOver
    }

    fun isGameWon(): Boolean {
        var numOfUnrevealedCells: Int = 0

        for(c in getMineGrid().getCells()) {
            if(c.getValue() != BOMB && c.getValue() != BLANK && !c.getRevealed()) {
                numOfUnrevealedCells++
            }
        }

        if(numOfUnrevealedCells == 0 && getBombsCounter() == getNumOfFlags()) {
            return true
        }
        return false
    }
}