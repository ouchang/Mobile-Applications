package com.example.calendar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EventAdapter(context : Context,resource : Int, list : ArrayList<Event>, weekView : WeekViewActivity)  : ArrayAdapter<Event>(context, resource, list) {
    val eventList = mutableListOf<Event>()
    val weekView = weekView

    init {
        eventList.addAll(list)
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var event : Event? = getItem(position)
        var tmpConvertView = convertView

        if(tmpConvertView == null) {
            tmpConvertView = LayoutInflater.from(context).inflate(R.layout.event, parent, false)
        }

        var eventTV : TextView = tmpConvertView!!.findViewById(R.id.eventTV)
        var eventTimeTV : TextView = tmpConvertView.findViewById(R.id.eventTimeTV)
        val eventTitle : String = event!!.name
        val eventTime : String = CalendarUtilis.formattedTime(event.time()!!)
        eventTV.text = eventTitle
        eventTimeTV.text = eventTime


        var deleteButton : Button = tmpConvertView!!.findViewById(R.id.deleteButton)
        deleteButton.tag = position
        deleteButton.setOnClickListener() {
            val selectedEvent = eventList.get(it.tag as Int)
            Toast.makeText(context, "Delete: "+selectedEvent.name, Toast.LENGTH_SHORT).show()
            EventUtils.eventList.remove(selectedEvent)

            var db = Room.databaseBuilder(
                context,
                EventDatabase::class.java,
                "event_database"
            ).allowMainThreadQueries().build()
            var eventDao = db.eventDao()

            GlobalScope.launch {
                eventDao.deleteEvent(selectedEvent)
            }

            notifyDataSetChanged()
            //weekView.setEventAdapter()

        }

        return tmpConvertView
    }
}