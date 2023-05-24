package com.example.actearn.database.dao

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.actearn.model.entity.Choices
import io.reactivex.rxjava3.core.Completable

@Dao
interface ChoicesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChoice(choices: Choices): Completable
}