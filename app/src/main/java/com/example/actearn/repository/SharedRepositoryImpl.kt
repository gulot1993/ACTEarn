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

    override fun saveStudentAnswer(questionId: Int, answerIndex: Int, isAnswerCorrect: Boolean): Completable {
        return database.studentAnswerDao().saveAnswer(StudentAnswer(userId = preferenceHelper.getLoggedInUser()!!.id, questionOwnerId = questionId, answerIndex = answerIndex, isAnswerCorrect = isAnswerCorrect))
    }

    override fun getAllAnswersByQuestion(questionId: Int, userId: Int): Single<List<StudentAnswer>> {
        return database.studentAnswerDao().getAllAnswersByQuestionIdAndUserId(questionId, userId)
    }

    override fun addReward(name: String, points: Int): Completable {
        return database.rewardsDao().saveReward(Reward(name = name, points = points))
    }

    override fun getRewards(): Single<List<Reward>> {
        return database.rewardsDao().getAllRewards()
    }

    override fun getAllAnswersByQuestionId(questionId: Int): Single<List<StudentAnswer>> {
        return database.studentAnswerDao().getAllAnswersByQuestionId(questionId)
    }

    override fun getDistinctUserIdFromAnswers(): Single<List<Int>> {
        return database.studentAnswerDao().getStudentsAnswerDistinctById()
    }

    override fun saveClaimedReward(rewardId: Int): Completable {
        return database.studentRewardClaimedDao().saveClaimedReward(StudentRewardClaimed(userId = preferenceHelper.getLoggedInUser()!!.id, rewardId = rewardId))
    }

    override fun getAllRewardsFromUser(): Single<List<StudentRewardClaimed>> {
        return database.studentRewardClaimedDao().getClaimedRewards(preferenceHelper.getLoggedInUser()!!.id)
    }

    override fun getAllStudentsRewards(studentId: Int): Single<List<StudentRewardClaimed>> {
        return database.studentRewardClaimedDao().getClaimedRewards(studentId)
    }

    override fun getReward(id: Int): Single<Reward> {
        return database.rewardsDao().getReward(id)
    }

    override fun getClaimedRewards(): Single<List<StudentRewardClaimed>> {
        return database.studentRewardClaimedDao().getAllClaimedRewards()
    }

    override fun updatePoints(points: Int): Completable {
        return database.pointsDao().updatePoints(points, preferenceHelper!!.getLoggedInUser()!!.id)
    }

    override fun getUserPoints(userId: Int): Single<Points> {
        return database.pointsDao().getUserPoints(userId)
    }

    override fun getUser(user: Int): Single<User> {
        return database.userDao().getUser(user)
    }

}