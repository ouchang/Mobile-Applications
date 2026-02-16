package com.example.calendar

import android.app.Application

class CalendarApplication : Application() {
    private val database by lazy { EventDatabase.getDatabase(this)}
    val repository by lazy { EventRepository(database.eventDao()) }
}