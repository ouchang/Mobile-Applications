package com.example.calendar

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Query("SELECT * FROM event_table ORDER BY id ASC")
    fun allEvents(): Flow<List<Event>>

    @Query("SELECT * FROM event_table WHERE date = :selectedDate")
    fun findEventsBasedOnDate(selectedDate : String) : List<Event>

    @Query("SELECT * FROM event_table WHERE date = :selectedDate AND time = :selectedTime")
    fun findEventsBasedOnDateTime(selectedDate : String, selectedTime : String) : List<Event>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: Event)

    @Delete
    suspend fun deleteEvent(event: Event)
}