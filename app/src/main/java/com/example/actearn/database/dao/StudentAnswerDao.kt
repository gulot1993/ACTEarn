package com.example.actearn.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.actearn.model.entity.StudentAnswer
import io.reactivex.rxjava3.core.Completable

@Dao
interface StudentAnswerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAnswer(answer: StudentAnswer): Completable
}