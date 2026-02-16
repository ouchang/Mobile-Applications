package com.example.calendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.calendar.CalendarUtilis.Companion.dayMonthFromDate
import com.example.calendar.CalendarUtilis.Companion.selectedDate
import java.time.LocalTime
import java.time.format.TextStyle
import java.util.*
import kotlin.collections.ArrayList

class DailyViewActivity : AppCompatActivity() {

    lateinit var dayMonthText : TextView
    lateinit var dayOfWeekTV : TextView
    lateinit var hourListView : ListView

    fun initWidgets() {
        dayMonthText = findViewById(R.id.dayMonthTV)
        dayOfWeekTV = findViewById(R.id.dayOfWeekTV)
        hourListView = findViewById(R.id.hourList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_view)
        initWidgets()
    }

    fun setDayView() {
        dayMonthText.text = dayMonthFromDate(CalendarUtilis.selectedDate)

        val dayOfWeek : String = selectedDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
        dayOfWeekTV.text = dayOfWeek

        setHourAdapter()
    }

    override fun onResume() {
        super.onResume()
        setDayView()
    }

    fun setHourAdapter() {
        val hourAdapter : HourAdapter = HourAdapter(applicationContext, 0, hourEventList(), this)
        hourListView.adapter = hourAdapter
    }

    fun hourEventList() : ArrayList<HourEvent> {
        val list : ArrayList<HourEvent> = ArrayList<HourEvent>()

        var db = Room.databaseBuilder(
            applicationContext,
            EventDatabase::class.java,
            "event_database"
        ).allowMainThreadQueries().build()
        var eventDao = db.eventDao()

        for(hour in 0 until 24 step 1) {
            val time : LocalTime = LocalTime.of(hour, 0)
            //val events : ArrayList<Event> = EventUtils.eventsForDateAndTime(selectedDate, time)
            var selectedDateStr : String = Event.dateFormatter.format(CalendarUtilis.selectedDate)
            var selectedTimeStr : String = Event.timeFormatter.format(time)
            val events = eventDao.findEventsBasedOnDateTime(selectedDateStr, selectedTimeStr)
            val hourEvent : HourEvent = HourEvent(time, events as ArrayList<Event>)
            list.add(hourEvent)
        }

        return list
    }

    fun previousDayAction(view: View) {
        CalendarUtilis.selectedDate = CalendarUtilis.selectedDate.minusDays(1)
        setDayView()
    }

    fun nextDayAction(view: View) {
        CalendarUtilis.selectedDate = CalendarUtilis.selectedDate.plusDays(1)
        setDayView()
    }

    fun newEventAction(view: View) {
        var eventIntent : Intent = Intent(this, EventEditActivity::class.java)
        startActivity(eventIntent)
    }
}