package com.example.actearn.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.actearn.model.entity.Reward
import io.reactivex.rxjava3.core.Completable

@Dao
interface RewardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveReward(reward: Reward): Completable
}