package com.estudante.repository

import android.content.Context
import android.net.Uri
import com.estudante.database.DatabaseInstance
import com.estudante.models.StudentCard
import com.estudante.models.StudentCardEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StudentCardRepository {
    fun saveStudentCard(studentCard: StudentCard, context: Context) {
        val dao = DatabaseInstance.getDatabase(context).studentCardDao()
        val entity = StudentCardEntity(
            primaryLogoUri = studentCard.primaryLogo?.toString(),
            secondaryLogoUri = studentCard.secondaryLogo?.toString(),
            profileImageUri = studentCard.profileImage?.toString(),
            studentName = studentCard.studentName,
            studentId = studentCard.studentId,
            studentCourse = studentCard.studentCourse,
            studentCicle = studentCard.studentCicle,
            year = studentCard.year,
            id= studentCard.studentId
        )

        CoroutineScope(Dispatchers.IO).launch {
            dao.insert(entity)
        }
    }

    suspend fun loadStudentCard(id: Long, context: Context): StudentCard? {
        val dao = DatabaseInstance.getDatabase(context).studentCardDao()
        val entity = withContext(Dispatchers.IO) { dao.getStudentCardById(id) }

        return entity?.let {
            StudentCard().apply {
                primaryLogo = it.primaryLogoUri?.let { uri -> Uri.parse(uri) }
                secondaryLogo = it.secondaryLogoUri?.let { uri -> Uri.parse(uri) }
                profileImage = it.profileImageUri?.let { uri -> Uri.parse(uri) }
                studentName = it.studentName
                studentId = it.studentId
                studentCourse = it.studentCourse
                studentCicle = it.studentCicle
                year = it.year
            }
        }
    }
}