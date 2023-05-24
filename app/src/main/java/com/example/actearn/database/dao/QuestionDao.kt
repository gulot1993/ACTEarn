package com.example.actearn.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.actearn.model.entity.Question
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface QuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuestion(question: Question): Completable

    @Query("SELECT * FROM Question where question = :question")
    fun getQuestionByQuestionDescription(question: String): Single<Question>
}