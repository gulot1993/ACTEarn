package com.example.actearn.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.actearn.model.entity.StudentRemarks
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface StudentRemarksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(remarks: StudentRemarks): Completable

    @Query("SELECT * FROM StudentRemarks where studentId = :studentId")
    fun getAllRemarksByUserId(studentId: Int): Single<List<StudentRemarks>>
}