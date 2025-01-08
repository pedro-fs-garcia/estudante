package com.estudante.models
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student_card_table")
data class StudentCardEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val primaryLogoUri: String?,
    val secondaryLogoUri: String?,
    val profileImageUri: String?,
    val studentName: String,
    val studentId: String,
    val studentCourse: String,
    val studentCicle: String,
    val year: String
)