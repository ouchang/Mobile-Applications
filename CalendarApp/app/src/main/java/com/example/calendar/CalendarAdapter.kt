package com.example.calendar

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate

class CalendarAdapter(inputDays: ArrayList<LocalDate?>, itemListener : OnItemListener) :
    RecyclerView.Adapter<CalendarViewHolder>() {

    private var days : ArrayList<LocalDate?> = inputDays
    private var onItemListener : OnItemListener = itemListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val inflater : LayoutInflater = LayoutInflater.from(parent.context)
        val view : View = inflater.inflate(R.layout.cell, parent, false)
        val layoutParams : ViewGroup.LayoutParams = view.layoutParams

        if(days.size > 15) { // month view
            layoutParams.height = (parent.height * 0.166666).toInt()
        } else { // week view
            layoutParams.height = (parent.height).toInt()
        }



        return CalendarViewHolder(view, onItemListener, days)
    }

    override fun getItemCount(): Int {
        return days.size
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val date = days.get(position)

        if(date != null) {
            holder.dayOfMonth.text = date.dayOfMonth.toString()

            if(date.equals(CalendarUtilis.selectedDate)) {
                holder.parentView.setBackgroundColor(Color.parseColor("#FFB8BF"))
            }

            if(date.month.equals(CalendarUtilis.selectedDate.month)) {
                holder.dayOfMonth.setTextColor(Color.BLACK)
            } else {
                holder.dayOfMonth.setTextColor(Color.LTGRAY)
            }
        }
    }

    interface OnItemListener {
        fun onItemClick(position : Int, date : LocalDate)
    }
}