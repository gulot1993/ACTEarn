package com.example.actearn.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.actearn.model.entity.Activity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface ActivityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveActivity(activity: Activity): Completable

    @Query("SELECT * FROM Activity where activityName = :name")
    fun getActivityByName(name: String): Single<Activity>

    @Query("SELECT * FROM Activity where userOwnerId = :profId and subject = :subject")
    fun getActivityBySubject(profId: Int, subject: String): Single<List<Activity>>
}