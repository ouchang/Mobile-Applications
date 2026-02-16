package com.example.calendar

import java.time.LocalDate
import java.time.LocalTime

class EventUtils {
    companion object {
        var eventList : ArrayList<Event> = ArrayList()

        fun eventsForDate(date : LocalDate) : ArrayList<Event> {
            var list : ArrayList<Event> = ArrayList()

            for(e in eventList) {
                if(e.date()!!.equals(date)) {
                    list.add(e)
                }
            }

            return list
        }

        fun eventsForDateAndTime(date : LocalDate, time : LocalTime) : ArrayList<Event> {
            var list : ArrayList<Event> = ArrayList()

            for(e in eventList) {
                var eventHour : Int = e.time()!!.hour
                var cellHour : Int = time.hour

                if(e.date()!!.equals(date) && eventHour.equals(cellHour)) {
                    list.add(e)
                }
            }

            return list
        }
    }
}