package com.example.actearn.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.actearn.database.dao.ActivityDao
import com.example.actearn.database.dao.PointsDao
import com.example.actearn.database.dao.UserDao
import com.example.actearn.model.entity.Points
import com.example.actearn.model.entity.User

@Database(entities = [
    User::class,
    Points::class
 ], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun pointsDao(): PointsDao

    abstract fun activityDao(): ActivityDao
}