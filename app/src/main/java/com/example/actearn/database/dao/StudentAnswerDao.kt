package com.example.actearn.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.actearn.model.entity.StudentAnswer
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface StudentAnswerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAnswer(answer: StudentAnswer): Completable

    @Query("SELECT * FROM StudentAnswer where questionOwnerId = :questionId and userId = :userId")
    fun getAllAnswersByQuestionIdAndUserId(questionId: Int, userId: Int): Single<StudentAnswer>

    @Query("SELECT * FROM StudentAnswer where questionOwnerId = :questionId")
    fun getAllAnswersByQuestionId(questionId: Int): Single<List<StudentAnswer>>

    @Query("SELECT DISTINCT userId from StudentAnswer")
    fun getStudentsAnswerDistinctById(): Single<List<Int>>

    @Query("SELECT * FROM StudentAnswer where userId = :userId")
    fun getAllStudentAnswers(userId: Int): Single<List<StudentAnswer>>
}