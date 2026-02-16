package com.example.calendar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.calendar.CalendarUtilis.Companion.formattedShortTime
import java.time.LocalTime

class HourAdapter (context : Context, resource : Int, list : ArrayList<HourEvent>, dayView : DailyViewActivity)  : ArrayAdapter<HourEvent>(context, resource, list) {
    var dayView = dayView
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var hourEvent : HourEvent? = getItem(position)
        var tmpConvertView = convertView

        if(tmpConvertView == null) {
            tmpConvertView = LayoutInflater.from(context).inflate(R.layout.hour, parent, false)
        }

        setHour(tmpConvertView!!, hourEvent!!.time)
        setEvents(tmpConvertView, hourEvent.events)

        return tmpConvertView
    }

    fun setHour(convertView : View, time : LocalTime) {
        val timeTV : TextView = convertView.findViewById(R.id.timeTV)
        timeTV.text = formattedShortTime(time)
    }

    fun setEvents(convertView : View, events : ArrayList<Event>) {
        val event1 : TextView = convertView.findViewById(R.id.scheduledEvent1)
        val event2 : TextView = convertView.findViewById(R.id.scheduledEvent2)
        val event3 : TextView = convertView.findViewById(R.id.scheduledEvent3)

        event1.setOnClickListener() {
            dayView.finish()
            true
        }

        event2.setOnClickListener() {
            dayView.finish()
            true
        }

        event3.setOnClickListener() {
            dayView.finish()
            true
        }

        when(events.size) {
            0 -> {
                hideEvent(event1)
                hideEvent(event2)
                hideEvent(event3)
            }
            1 -> {
                setEvent(event1, events.get(0))
                hideEvent(event2)
                hideEvent(event3)
            }
            2 ->{
                setEvent(event1, events.get(0))
                setEvent(event2, events.get(1))
                hideEvent(event3)
            }
            3 -> {
                setEvent(event1, events.get(0))
                setEvent(event2, events.get(1))
                setEvent(event3, events.get(2))
            }
            else -> {
                setEvent(event1, events.get(0))
                setEvent(event2, events.get(1))

                val eventsNotShown : String = "" + (events.size - 2).toString() + " More Events"
                event3.text = eventsNotShown
                event3.visibility = View.VISIBLE
            }
        }
    }

    fun hideEvent(tv : TextView) {
        tv.visibility = View.INVISIBLE
    }

    fun setEvent(tv : TextView, event : Event) {
        tv.text = event.name
        tv.visibility = View.VISIBLE
    }
}