package com.example.actearn.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.actearn.database.dao.*
import com.example.actearn.model.entity.*

@Database(entities = [
    User::class,
    Points::class,
    Activity::class,
    Question::class,
    Choices::class,
    StudentAnswer::class,
    Reward::class,
    StudentRewardClaimed::class,
    Subject::class,
    StudentRemarks::class
 ], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun pointsDao(): PointsDao
    abstract fun activityDao(): ActivityDao

    abstract fun questionDao(): QuestionDao

    abstract fun choicesDao(): ChoicesDao

    abstract fun studentAnswerDao(): StudentAnswerDao

    abstract fun rewardsDao(): RewardDao

    abstract fun studentRewardClaimedDao(): StudentRewardDao

    abstract fun subjectDao(): SubjectDao

    abstract fun studentRemarksDao(): StudentRemarksDao
}