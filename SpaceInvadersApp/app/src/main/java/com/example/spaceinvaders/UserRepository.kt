package com.example.spaceinvaders

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao : UserDao) {
    @WorkerThread
    suspend fun insertUser(user : User) {
        userDao.insertUser(user)
    }

    @WorkerThread
    fun isUsernameTaken(username : String) : Boolean {
        return userDao.isUsernameTaken(username)
    }

    @WorkerThread
    fun logIn(username : String, password : String) : Boolean {
        return userDao.logIn(username, password)
    }
}