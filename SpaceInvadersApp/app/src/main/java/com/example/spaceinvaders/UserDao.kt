package com.example.spaceinvaders

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {
    @Insert
    fun insertUser(user : User)

    @Query("SELECT EXISTS (SELECT * FROM user_table WHERE username=:username)")
    fun isUsernameTaken(username : String) : Boolean

    @Query("SELECT EXISTS (SELECT * FROM user_table WHERE username=:username AND password=:password)")
    fun logIn(username : String, password : String) : Boolean
}