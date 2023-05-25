package com.example.actearn.database.dao

import androidx.room.*
import com.example.actearn.model.entity.Question
import com.example.actearn.model.entity.QuestionWithChoices
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface QuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuestion(question: Question): Completable

    @Query("SELECT * FROM Question where question = :question")
    fun getQuestionByQuestionDescription(question: String): Single<Question>

    @Query("SELECT * FROM Question where activityId = :activityId")
    fun getQuestionsByActivityId(activityId: Int): Single<List<Question>>

    @Transaction
    @Query("SELECT * FROM Question where questionId = :questionId")
    fun getQuestions(questionId: Int): Single<List<QuestionWithChoices>>
}