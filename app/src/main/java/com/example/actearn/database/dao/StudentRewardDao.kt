package com.example.actearn.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.actearn.model.entity.StudentRewardClaimed
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface StudentRewardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveClaimedReward(data: StudentRewardClaimed): Completable

    @Query("SELECT * FROM StudentRewardClaimed where userId = :userId")
    fun getClaimedRewards(userId: Int): Single<List<StudentRewardClaimed>>

    @Query("SELECT * FROM StudentRewardClaimed")
    fun getAllClaimedRewards(): Single<List<StudentRewardClaimed>>
}