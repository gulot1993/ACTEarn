package com.example.actearn.repository

import com.example.actearn.model.entity.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface SharedRepository {
    fun isLoggedIn(): Single<Boolean>

    fun signUp(
        username: String,
        password: String,
        fName: String,
        lName: String,
        role: String
    ): Completable

    fun signIn(
        username: String,
        password: String
    ): Single<User>

    fun saveUser(user: com.example.actearn.model.domain.User)

    fun getLoggedInUser(): com.example.actearn.model.domain.User?

    fun setIsLoggedIn(isLoggedIn: Boolean)

    fun updateUser(user: com.example.actearn.model.domain.User): Completable

    fun savePoints(points: Int, userOwnerId: Int): Completable

    fun getUserAndPoints(userId: Int): Single<List<UserWithPoint>>

    fun saveActivity(activityName: String, subject: String): Completable

    fun getActivityByName(activityName: String): Single<Activity>

    fun saveQuestion(question: String, activityId: Int, choicesCorrectAnswerIndex: Int): Completable

    fun getQuestionByDescription(question: String): Single<Question>

    fun saveChoices(choices: Choices): Completable

    fun getAllProfessors(): Single<List<User>>

    fun getActivitiesByProfAndSubject(profId: Int, subject: String): Single<List<Activity>>

    fun getQuestionsByActivityId(activityId: Int): Single<List<Question>>

    fun getAllQuestions(questionId: Int): Single<List<QuestionWithChoices>>

    fun saveStudentAnswer(questionId: Int, answerIndex: Int, isAnswerCorrect: Boolean): Completable

    fun getAllAnswersByQuestion(questionId: Int): Single<List<StudentAnswer>>

    fun addReward(name: String, points: Int): Completable

    fun getRewards(): Single<List<Reward>>
}