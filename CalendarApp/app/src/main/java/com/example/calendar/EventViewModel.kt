package com.example.calendar

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class EventViewModel() : ViewModel() {
    var eventItems = MutableLiveData<MutableList<Event>>()

    init {
        eventItems.value = mutableListOf()
    }

    fun addEvent(newEvent : Event) {
        val list = eventItems.value
        list!!.add(newEvent)
        eventItems.postValue(list!!)
    }
}

