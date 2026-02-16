package com.example.calendar

import android.util.Log
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.ArrayList

class CalendarUtilis {
    companion object {
        lateinit var selectedDate: LocalDate

        fun monthYearFromDate(date : LocalDate): String? {
            val formatter : DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")
            return date.format(formatter)
        }

        fun dayMonthFromDate(date : LocalDate): String? {
            val formatter : DateTimeFormatter = DateTimeFormatter.ofPattern("d MMMM ")
            return date.format(formatter)
        }

        fun daysInMonthArray() : ArrayList<LocalDate?> {
            val days : ArrayList<LocalDate?> = ArrayList<LocalDate?>()

            val yearMonth : YearMonth = YearMonth.from(selectedDate)
            val daysInMonth : Int = yearMonth.lengthOfMonth()

            val prevMonth : LocalDate = selectedDate.minusMonths(1)
            val nextMonth : LocalDate = selectedDate.plusMonths(1)

            val prevYearMonth : YearMonth = YearMonth.from(prevMonth)
            val prevDaysInMonth : Int = prevYearMonth.lengthOfMonth()

            val firstOfMonth : LocalDate = CalendarUtilis.selectedDate.withDayOfMonth(1)
            val dayOfWeek : Int = firstOfMonth.dayOfWeek.value

            for (i in 1 until 43 step 1) {
                if(i < dayOfWeek) {
                    days.add(LocalDate.of(prevMonth.year, prevMonth.month, prevDaysInMonth + i - dayOfWeek + 1))
                } else if(i >= daysInMonth + dayOfWeek) {
                    days.add(LocalDate.of(nextMonth.year, nextMonth.month, i - dayOfWeek + 1 - daysInMonth))
                } else {
                    days.add(LocalDate.of(selectedDate.year, selectedDate.month, i - dayOfWeek + 1))
                }
            }
            return days
        }

        fun daysInWeekArray(date : LocalDate) : ArrayList<LocalDate?> {
            val days : ArrayList<LocalDate?> = ArrayList<LocalDate?>()
            var current : LocalDate = mondayForDate(selectedDate)
            val endDate : LocalDate = current.plusWeeks(1)

            while(current.isBefore(endDate)) {
                days.add(current)
                current = current.plusDays(1)
            }
            return days
        }

        private fun mondayForDate(selectedDate : LocalDate): LocalDate {
            var current = selectedDate
            val oneWeekAgo : LocalDate = current.minusWeeks(1)

            while(current.isAfter(oneWeekAgo)) {
                if(current.dayOfWeek == DayOfWeek.MONDAY) {
                    return current
                }

                current = current.minusDays(1)
            }
            return selectedDate // default value
        }

        fun formattedDate(date: LocalDate) : String {
            val formatter : DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
            return date.format(formatter)
        }

        fun formattedTime(time: LocalTime) : String {
            val formatter : DateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a")
            return time.format(formatter)
        }

        fun formattedShortTime(time: LocalTime) : String {
            val formatter : DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
            return time.format(formatter)
        }
    }
}