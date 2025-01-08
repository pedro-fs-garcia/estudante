package com.estudante.models
import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student_card_table")
data class StudentCardEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val primaryLogoUri: String?,
    val secondaryLogoUri: String?,
    val profileImageUri: String?,
    val studentName: String,
    val studentId: Long,
    val studentCourse: String,
    val studentCicle: String,
    val year: String
)