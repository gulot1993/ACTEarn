package com.example.actearn.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.actearn.model.entity.Subject
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface SubjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(subject: Subject): Completable

    @Query("SELECT * FROM Subject where id = :subjectId")
    fun get(subjectId: Int): Single<Subject>

    @Query("SELECT * FROM Subject")
    fun all(): Single<List<Subject>>
}