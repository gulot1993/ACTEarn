package com.example.actearn.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.actearn.model.entity.Points
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface PointsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePoints(points: Points): Completable

    @Query("UPDATE Points SET points = :points where userOwnerId = :userId")
    fun updatePoints(points: Int, userId: Int): Completable

    @Query("SELECT * FROM Points where userOwnerId = :userId")
    fun getUserPoints(userId: Int): Single<Points>
}