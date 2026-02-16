package com.example.calendar

import android.view.View
import android.view.ViewStub.OnInflateListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate

class CalendarViewHolder(itemView : View, itemListener: CalendarAdapter.OnItemListener, inputDays: ArrayList<LocalDate?>) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private var days : ArrayList<LocalDate?> = inputDays
    var dayOfMonth: TextView =itemView.findViewById(R.id.cellDayText)
    var onItemListener : CalendarAdapter.OnItemListener = itemListener
    var parentView : View = itemView.findViewById(R.id.parentView)

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(view : View?) {
        days.get(adapterPosition)?.let { onItemListener.onItemClick(adapterPosition, it) }
    }
}