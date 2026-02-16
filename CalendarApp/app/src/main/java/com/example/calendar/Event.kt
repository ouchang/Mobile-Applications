package com.example.calendar

import androidx.annotation.Discouraged
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

//@Entity(tableName = "EventsTable")
//class Event(@PrimaryKey @ColumnInfo(name = "Title") val nameE : String, @ColumnInfo(name = "Date") val dateE : LocalDate, @ColumnInfo(name = "Time") val timeE : LocalTime) {

@Entity(tableName = "event_table")
class Event(@ColumnInfo(name = "name") var name : String, @ColumnInfo(name = "date") var dateStr : String, @ColumnInfo(name = "time") var timeStr : String, @PrimaryKey(autoGenerate = true) var id : Int = 0) {
    /*
    companion object {
        var eventList : ArrayList<Event> = ArrayList<Event>()

        fun eventsForDate(date : LocalDate) : ArrayList<Event> {
            var list : ArrayList<Event> = ArrayList<Event>()

            for(e in eventList) {
                if(e.date.equals(date)) {
                    list.add(e)
                }
            }

            return list
        }

        fun eventsForDateAndTime(date : LocalDate, time : LocalTime) : ArrayList<Event> {
            var list : ArrayList<Event> = ArrayList<Event>()

            for(e in eventList) {
                var eventHour : Int = e.time.hour
                var cellHour : Int = time.hour

                if(e.date.equals(date) && eventHour.equals(cellHour)) {
                    list.add(e)
                }
            }

            return list
        }
    }
     */

    fun date() : LocalDate? = if(dateStr == null) null else LocalDate.parse(dateStr, dateFormatter)
    fun time() : LocalTime? = if(timeStr == null) null else LocalTime.parse(timeStr, timeFormatter)

    companion object {
        val timeFormatter : DateTimeFormatter = DateTimeFormatter.ISO_TIME
        val dateFormatter : DateTimeFormatter = DateTimeFormatter.ISO_DATE
    }

        /*
    internal var name : String = nameE
        get() {
            return field
        }
        set(value : String) {
            field = value
        }

    internal var date : LocalDate = dateE
        get() {
            return field
        }
        set(value : LocalDate) {
            field = value
        }

    internal var time :LocalTime = timeE
        get() {
            return field
        }
        set(value : LocalTime) {
            field = value
        }
         */
}