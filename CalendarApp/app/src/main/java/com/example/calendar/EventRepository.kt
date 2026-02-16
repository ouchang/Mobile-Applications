package com.example.calendar

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class EventRepository(private val eventDao: EventDao) {
    val allEvents : Flow<List<Event>> = eventDao.allEvents()

    @WorkerThread
    suspend fun insertEvent(event : Event) {
        eventDao.insertEvent(event)
    }

    @WorkerThread
    suspend fun deleteEvent(event : Event) {
        eventDao.deleteEvent(event)
    }

    @WorkerThread
    fun findEventsBasedOnDate(selectedDate : String) : List<Event> {
        return eventDao.findEventsBasedOnDate(selectedDate)
    }

    @WorkerThread
    fun findEventsBasedOnDateTime(selectedDate : String, selectedTime : String) : List<Event> {
        return eventDao.findEventsBasedOnDateTime(selectedDate, selectedTime)
    }
}