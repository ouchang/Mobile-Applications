package com.example.saper

import android.hardware.camera2.params.BlackLevelPattern
import android.util.Log
import kotlin.random.Random

class MineGrid() {
    private var size: Int = 9
    private var cells: ArrayList<Cell> = ArrayList<Cell>()

    init { // fill board with blank spaces
        for(i in 0 until size*size step 1) {
            cells.add(Cell(BLANK))
        }
    }

    fun getCells(): ArrayList<Cell> {
        return cells
    }

    fun generateGrid(numOfBombs: Int) {
        var numOfBombsPlaced: Int = 0

        while(numOfBombsPlaced < numOfBombs) {
            // get random place for bomb
            var x: Int = Random.nextInt(size) // kolumna
            var y: Int = Random.nextInt(size) // wiersz
            var idx: Int = toIndex(x, y)

            // place bomb
            if(cells[idx].getValue() == BLANK) {
                cells[idx] = Cell(BOMB)
                numOfBombsPlaced++
            }
        }

        // get cell's neighbours
        for(x in 0 until size step 1) {
            for(y in 0 until size step 1) {
                if(cellAt(x,y).getValue() != BOMB) {
                    var adjacentCells: ArrayList<Cell> = getAdjacentCells(x,y)
                    var countBombs: Int = 0

                    for(c in adjacentCells) {
                        if(c.getValue() == BOMB) {
                            countBombs++
                        }
                    }

                    if(countBombs > 0) {
                        cells[x+(y*size)] = Cell(countBombs)
                    }
                }
            }
        }
    }

    fun getAdjacentCells(x: Int, y: Int) : ArrayList<Cell> {
        var adjacentCells: ArrayList<Cell> = ArrayList<Cell>()
        var listOfCells: ArrayList<Cell> = ArrayList<Cell>()

        listOfCells.add(cellAt(x,y+1)) // down
        listOfCells.add(cellAt(x,y-1)) // up
        listOfCells.add(cellAt(x+1,y+1)) // left down
        listOfCells.add(cellAt(x+1,y-1)) // right down
        listOfCells.add(cellAt(x-1,y+1)) // left up
        listOfCells.add(cellAt(x-1,y-1)) // right up
        listOfCells.add(cellAt(x-1,y)) // left
        listOfCells.add(cellAt(x+1,y)) // right


        for(c in listOfCells) {
            adjacentCells.add(c)
        }

        return adjacentCells
    }

    fun revealAllBombs() {
        for(c in cells) {
            if(c.getValue() == BOMB) {
                c.setRevealed(true)
            }
        }
    }

    fun cellAt(x: Int, y: Int): Cell {
        if(x < 0 || x >= size || y < 0 || y >= size) {
            return Cell(-2) // outside the grid
        }
        return cells[x+(y*size)]
    }

    fun toIndex(x: Int, y: Int): Int {
        return x + (y*size)
    }

    fun toXY(idx: Int): IntArray {
        var y: Int = idx / size
        var x: Int = idx - (y*size)
        return intArrayOf(x, y)
    }
}