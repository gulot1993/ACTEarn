package com.example.actearn.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.actearn.model.entity.Points
import io.reactivex.rxjava3.core.Completable

@Dao
interface PointsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePoints(points: Points): Completable
}