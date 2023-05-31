package com.example.actearn.database.dao

import androidx.room.*
import com.example.actearn.model.entity.User
import com.example.actearn.model.entity.UserWithActivity
import com.example.actearn.model.entity.UserWithPoint
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(user: User): Completable

    @Query("SELECT * FROM User WHERE username = :username and password = :password LIMIT 1")
    fun getUser(username: String, password: String): Single<User>

    @Query("UPDATE User SET hasClaimedPoints = :hasClaimedPoints where userId = :userId")
    fun updateUserHasClaimedPoints(hasClaimedPoints: Boolean, userId: Int): Completable

    @Transaction
    @Query("SELECT * FROM User where userId = :userId")
    fun getUserAndPoints(userId: Int): Single<List<UserWithPoint>>

    @Query("SELECT * FROM User where role = 'Professor'")
    fun getAllProfessors(): Single<List<User>>

    @Transaction
    @Query("SELECT * FROM User where userId = :userId")
    fun getAllProfessorActivities(userId: Int): Single<List<UserWithActivity>>

    @Query("SELECT * FROM User where userId = :userId")
    fun getUser(userId: Int): Single<User>
}