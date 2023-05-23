package com.example.actearn.database.dao

import androidx.room.*
import com.example.actearn.model.entity.User
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
}