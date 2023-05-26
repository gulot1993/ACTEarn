package com.example.actearn.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.actearn.model.entity.Reward
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface RewardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveReward(reward: Reward): Completable

    @Query("SELECT * FROM Reward")
    fun getAllRewards(): Single<List<Reward>>

    @Query("SELECT * FROM Reward where id = :id")
    fun getReward(id: Int): Single<Reward>
}