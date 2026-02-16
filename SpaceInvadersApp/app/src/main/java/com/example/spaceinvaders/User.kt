package com.example.spaceinvaders

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
class User(
    @PrimaryKey(autoGenerate = true) var id : Int,
    @ColumnInfo(name = "username") var username : String,
    @ColumnInfo(name = "password") var password : String,
) {

}