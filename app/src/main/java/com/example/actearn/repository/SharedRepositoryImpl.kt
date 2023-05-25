package com.example.actearn.repository

import com.example.actearn.core.PreferenceHelper
import com.example.actearn.database.AppDatabase
import com.example.actearn.model.entity.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class SharedRepositoryImpl @Inject constructor(
    val preferenceHelper: PreferenceHelper,
    val database: AppDatabase
): SharedRepository {
    override fun isLoggedIn(): Single<Boolean> {
        return Single.just(preferenceHelper.getIsLoggedIn())
    }

    override fun signUp(
        username: String,
        password: String,
        fName: String,
        lName: String,
        role: String
    ): Completable {
        val entity = User(
            username = username,
            password = password,
            firstname = fName,
            lastname = lName,
            role = role,
            hasClaimedPoints = false
        )

        return database.userDao().saveUser(entity)
    }

    override fun signIn(username: String, password: String): Single<User> {
        return database.userDao().getUser(username, password)
    }

    override fun saveUser(user: com.example.actearn.model.domain.User) {
        preferenceHelper.setLoggedInUser(user)
    }

    override fun getLoggedInUser(): com.example.actearn.model.domain.User? {
        return preferenceHelper.getLoggedInUser()
    }

    override fun setIsLoggedIn(isLoggedIn: Boolean) {
        preferenceHelper.setIsLoggedIn(isLoggedIn)
    }

    override fun updateUser(user: com.example.actearn.model.domain.User): Completable {
        return database.userDao().updateUserHasClaimedPoints(user.hasClaimedPoints, user.id)
    }

    override fun savePoints(points: Int, userOwnerId: Int): Completable {
        return database.pointsDao().savePoints(Points(userOwnerId = userOwnerId, points = points))
    }

    override fun getUserAndPoints(userId: Int): Single<List<UserWithPoint>> {
        return database.userDao().getUserAndPoints(userId)
    }

    override fun saveActivity(activityName: String, subject: String): Completable {
        return database.activityDao().saveActivity(Activity(activityName = activityName, subject = subject, userOwnerId = preferenceHelper.getLoggedInUser()?.id!!))
    }

    override fun getActivityByName(activityName: String): Single<Activity> {
        return database.activityDao().getActivityByName(activityName)
    }

    override fun saveQuestion(question: String, activityId: Int, choicesCorrectAnswerIndex: Int): Completable {
        return database.questionDao().insertQuestion(Question(question = question, activityOwnerId = activityId, choicesCorrectAnswerIndex = choicesCorrectAnswerIndex))
    }

    override fun getQuestionByDescription(question: String): Single<Question> {
        return database.questionDao().getQuestionByQuestionDescription(question)
    }

    override fun saveChoices(choices: Choices): Completable {
        return database.choicesDao().insertChoice(choices)
    }

    override fun getAllProfessors(): Single<List<User>> {
        return database.userDao().getAllProfessors()
    }

    override fun getActivitiesByProfAndSubject(profId: Int, subject: String): Single<List<Activity>> {
        return database.activityDao().getActivityBySubject(profId, subject)
    }

    override fun getQuestionsByActivityId(activityId: Int): Single<List<Question>> {
        return database.questionDao().getQuestionsByActivityId(activityId)
    }

    override fun getAllQuestions(questionId: Int): Single<List<QuestionWithChoices>> {
        return database.questionDao().getQuestions(questionId)
    }
}