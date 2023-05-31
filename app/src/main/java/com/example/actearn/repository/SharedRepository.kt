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

    fun saveActivity(activityName: String, subjectId: Int): Completable

    fun getActivityByName(activityName: String): Single<Activity>

    fun saveQuestion(question: String, activityId: Int, choicesCorrectAnswerIndex: Int): Completable

    fun getQuestionByDescription(question: String): Single<Question>

    fun saveChoices(choices: Choices): Completable

    fun getAllProfessors(): Single<List<User>>

    fun getActivitiesByProfAndSubject(profId: Int, subjectId: Int): Single<List<Activity>>

    fun getQuestionsByActivityId(activityId: Int): Single<List<Question>>

    fun getAllQuestions(questionId: Int): Single<List<QuestionWithChoices>>

    fun saveStudentAnswer(questionId: Int, answerIndex: Int, isAnswerCorrect: Boolean): Completable

    fun getAllAnswersByQuestion(questionId: Int, userId: Int): Single<StudentAnswer>

    fun addReward(name: String, points: Int): Completable

    fun getRewards(): Single<List<Reward>>

    fun getAllAnswersByQuestionId(questionId: Int): Single<List<StudentAnswer>>

    fun getDistinctUserIdFromAnswers(): Single<List<Int>>

    fun saveClaimedReward(rewardId: Int): Completable

    fun getAllRewardsFromUser(): Single<List<StudentRewardClaimed>>

    fun getAllStudentsRewards(studentId: Int): Single<List<StudentRewardClaimed>>

    fun getReward(id: Int): Single<Reward>

    fun getClaimedRewards(): Single<List<StudentRewardClaimed>>

    fun updatePoints(points: Int): Completable

    fun getUserPoints(userId: Int): Single<Points>

    fun getUser(user: Int): Single<User>

    fun saveSubject(subject: Subject): Completable

    fun getSubject(id: Int): Single<Subject>

    fun getAllSubject(): Single<List<Subject>>

    fun getAllStudentAnswers(): Single<List<StudentAnswer>>

    fun saveRemarks(activityId: Int, remarks: String, studentId: Int): Completable

    fun getActivityRemarksByProfAndSubject(profId: Int, subjectId: Int): Single<List<ActivityWithRemarks>>

    fun getAllActivities(): Single<List<Activity>>
}