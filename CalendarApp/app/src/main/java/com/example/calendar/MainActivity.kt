package com.example.calendar

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar.CalendarUtilis.Companion.daysInMonthArray
import com.example.calendar.CalendarUtilis.Companion.monthYearFromDate
import com.example.calendar.CalendarUtilis.Companion.selectedDate
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity(), CalendarAdapter.OnItemListener {
    lateinit var monthYearText : TextView
    lateinit var calendarRecyclerView: RecyclerView

    lateinit var datePickerDialog : DatePickerDialog

    fun initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView)
        monthYearText = findViewById(R.id.monthYearTV)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initWidgets()
        initDatePicker()

        if(savedInstanceState != null) {
            var currentDate : LocalDate = LocalDate.parse(savedInstanceState.getString("date"))
            selectedDate = currentDate
        } else {
            CalendarUtilis.selectedDate = LocalDate.now(ZoneId.of("Europe/Warsaw"))
        }
        Log.i("DateNow", CalendarUtilis.selectedDate.toString() + " " + CalendarUtilis.selectedDate.dayOfWeek)
        setMonthView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("date", selectedDate.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        var currentDate : LocalDate = LocalDate.parse(savedInstanceState!!.getString("date"))
        selectedDate = currentDate
    }

    fun setMonthView(){
        monthYearText.text = monthYearFromDate(CalendarUtilis.selectedDate)

        val daysInMonth : ArrayList<LocalDate?> = daysInMonthArray()
        val calendarAdapter : CalendarAdapter = CalendarAdapter(daysInMonth, this)
        val layoutManager : RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7)

        calendarRecyclerView.layoutManager = layoutManager
        calendarRecyclerView.adapter = calendarAdapter
    }

    fun previousMonthAction(view : View) {
        CalendarUtilis.selectedDate = CalendarUtilis.selectedDate.minusMonths(1)
        setMonthView()
    }

    fun nextMonthAction(view : View) {
        CalendarUtilis.selectedDate = CalendarUtilis.selectedDate.plusMonths(1)
        setMonthView()
    }

    override fun onItemClick(position: Int, date : LocalDate) {
        CalendarUtilis.selectedDate = date
        setMonthView()
    }


    fun initDatePicker() {
        val dateSetListener = DatePickerDialog.OnDateSetListener() { datePicker, year, month, day ->
            var selectedMonthNum : Int = CalendarUtilis.selectedDate.monthValue
            var m = month + 1

            if(m > selectedMonthNum) {
                var diff : Long = (month + 1 - selectedMonthNum).toLong()
                Log.i("DatePicker", "PLUS $diff")
                CalendarUtilis.selectedDate = CalendarUtilis.selectedDate.plusMonths(diff)
            } else {
                var diff : Long = ((month + 1 - selectedMonthNum)*(-1)).toLong()
                Log.i("DatePicker", "MINUS $diff")
                CalendarUtilis.selectedDate = CalendarUtilis.selectedDate.minusMonths(diff)
            }

            if(year > CalendarUtilis.selectedDate.year) {
                var diff : Long = (year - CalendarUtilis.selectedDate.year).toLong()
                CalendarUtilis.selectedDate = CalendarUtilis.selectedDate.plusYears(diff)
            }  else {
                var diff : Long = ((year - CalendarUtilis.selectedDate.year)*(-1)).toLong()
                CalendarUtilis.selectedDate = CalendarUtilis.selectedDate.minusYears(diff)
            }

            selectedMonthNum = CalendarUtilis.selectedDate.monthValue
            Log.i("DatePicker", "$selectedMonthNum | $m")
            setMonthView()
        }

        val cal = Calendar.getInstance()
        val year = cal[Calendar.YEAR]
        val month = cal[Calendar.MONTH]
        val day = cal[Calendar.DAY_OF_MONTH]

        val style : Int = AlertDialog.THEME_HOLO_LIGHT

        datePickerDialog = DatePickerDialog(this, style, dateSetListener, year, month, day)
    }

    fun selectDate(view: View) {
        datePickerDialog.show()
    }

    fun weeklyAction(view: View) {
        var myIntent : Intent = Intent(this, WeekViewActivity::class.java)
        startActivity(myIntent)
    }
}