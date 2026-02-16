package com.example.calendar

import java.time.LocalTime

class HourEvent(inputTime : LocalTime, inputEvents : ArrayList<Event>) {
    var time : LocalTime = inputTime
    var events : ArrayList<Event> = inputEvents
}