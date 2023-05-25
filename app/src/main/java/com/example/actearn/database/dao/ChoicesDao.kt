package com.example.actearn.database.dao

import androidx.room.*
import com.example.actearn.model.entity.Choices
import io.reactivex.rxjava3.core.Completable

@Dao
interface ChoicesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChoice(choices: Choices): Completable

//    @Query("SELECT * FROM Choices where")
//    fun getChoicesByQuestionId(questionId: Int)
}