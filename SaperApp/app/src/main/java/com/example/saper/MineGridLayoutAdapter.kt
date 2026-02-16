package com.example.saper

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MineGridLayoutAdapter(c: ArrayList<Cell>, l: OnCellClickHandler) :
    RecyclerView.Adapter<MineGridLayoutAdapter.MineTileViewHolder>() {

    private var cells: ArrayList<Cell> = c
    private var listener: OnCellClickHandler = l

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MineTileViewHolder {
        var itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.cell, parent, false)
        return MineTileViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return cells.size
    }

    override fun onBindViewHolder(holder: MineTileViewHolder, position: Int) {
        holder.bind(cells[position])
        holder.setIsRecyclable(false)
    }

    fun getCellsCount(): Int {
        return cells.size
    }

    fun setCells(c: ArrayList<Cell>) {
        cells = c
        this.notifyDataSetChanged()
    }

    inner class MineTileViewHolder(cellView: View) : RecyclerView.ViewHolder(cellView) {
        var valueTextView: TextView

        init {
            valueTextView = cellView.findViewById(R.id.cell_value)
        }

        fun bind(cell: Cell) {
            if(cell.getValue() == -2) {
                return
            }

            itemView.setBackgroundColor(Color.GRAY)
            itemView.setOnClickListener { listener.onCellClick(cell) } // lambda

            if(cell.getRevealed()) {
                if (cell.getValue() == BOMB) {
                    //valueTextView.text = R.string.bomb
                    valueTextView.text = "\uD83D\uDCA3"
                } else if (cell.getValue() == BLANK) {
                    valueTextView.text = ""
                    itemView.setBackgroundColor(Color.WHITE)
                } else {
                    valueTextView.text = cell.getValue().toString()

                    if (cell.getValue() == 1) {
                        itemView.setBackgroundColor(Color.CYAN)
                    } else if (cell.getValue() == 2) {
                        itemView.setBackgroundColor(Color.GREEN)
                    } else if (cell.getValue() == 3) {
                        itemView.setBackgroundColor(Color.RED)
                    }
                }
            } else if(cell.getFlagged()) {
                valueTextView.text = "\uD83D\uDEA9"
            }
        }
    }
}