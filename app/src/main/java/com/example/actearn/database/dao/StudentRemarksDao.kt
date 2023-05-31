package com.example.actearn.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.actearn.model.entity.StudentRemarks
import io.reactivex.rxjava3.core.Completable

@Dao
interface StudentRemarksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(remarks: StudentRemarks): Completable
}