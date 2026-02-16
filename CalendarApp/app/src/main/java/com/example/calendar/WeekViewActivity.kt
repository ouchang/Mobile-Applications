package com.example.calendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.calendar.databinding.ActivityMainBinding
import java.time.LocalDate
import kotlin.collections.ArrayList

class WeekViewActivity : AppCompatActivity(), CalendarAdapter.OnItemListener {
    lateinit var monthYearText : TextView
    lateinit var calendarRecyclerView: RecyclerView
    lateinit var eventListView : ListView
    //lateinit var eventDatabase : EventDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_week_view)
        //eventDatabase = EventDatabase.getDatabase(this)!!
        initWidgets()
        setWeekView()
    }

    fun initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView)
        monthYearText = findViewById(R.id.monthYearTV)
        eventListView = findViewById(R.id.eventList)
    }

    fun newEventAction(view: View) {
        var eventIntent : Intent = Intent(this, EventEditActivity::class.java)
        startActivity(eventIntent)
    }

    override fun onResume() {
        super.onResume()
        setEventAdapter()
    }

    fun nextWeekAction(view: View) {
        CalendarUtilis.selectedDate = CalendarUtilis.selectedDate.plusWeeks(1)
        setWeekView()
    }

    fun previousWeekAction(view: View) {
        CalendarUtilis.selectedDate = CalendarUtilis.selectedDate.minusWeeks(1)
        setWeekView()
    }


    fun setWeekView(){
        monthYearText.text = CalendarUtilis.monthYearFromDate(CalendarUtilis.selectedDate)

        val daysInWeek : ArrayList<LocalDate?> =
            CalendarUtilis.daysInWeekArray(CalendarUtilis.selectedDate)
        val calendarAdapter : CalendarAdapter = CalendarAdapter(daysInWeek, this)
        val layoutManager : RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7)

        calendarRecyclerView.layoutManager = layoutManager
        calendarRecyclerView.adapter = calendarAdapter
        setEventAdapter()
    }

    override fun onItemClick(position: Int, date : LocalDate) {
        CalendarUtilis.selectedDate = date
        setWeekView()
    }

    fun setEventAdapter() {
        //var dailyEvents: ArrayList<Event> = EventUtils.eventsForDate(CalendarUtilis.selectedDate)

        var db = Room.databaseBuilder(
            applicationContext,
            EventDatabase::class.java,
            "event_database"
        ).allowMainThreadQueries().build()
        var eventDao = db.eventDao()

        var selectedDateStr : String = Event.dateFormatter.format(CalendarUtilis.selectedDate)
        var dailyEvents : List<Event> =  eventDao.findEventsBasedOnDate(selectedDateStr)
        var eventAdapter: EventAdapter = EventAdapter(applicationContext, 0, dailyEvents as ArrayList<Event>, this)
        eventListView.adapter = eventAdapter
    }

    fun daillyAction(view: View) {
        var myIntent : Intent = Intent(this, DailyViewActivity::class.java)
        startActivity(myIntent)
    }


}