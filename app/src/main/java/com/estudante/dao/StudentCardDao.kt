package com.estudante.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.estudante.models.StudentCardEntity

@Dao
interface StudentCardDao {
    @Insert
    suspend fun insert(studentCard: StudentCardEntity)

    @Query("SELECT * FROM student_card_table WHERE id = :id")
    suspend fun getStudentCardById(id: Long): StudentCardEntity?

    @Update
    suspend fun update(studentCard: StudentCardEntity)

    @Query("DELETE FROM student_card_table WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM student_card_table")
    suspend fun getAllStudentCards(): List<StudentCardEntity>
}