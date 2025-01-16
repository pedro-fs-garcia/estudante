package com.estudante.repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.estudante.database.StudentCardDatabaseHelper
import com.estudante.models.StudentCard
import java.io.File

class StudentCardRepository(context: Context) {
    private val dbHelper = StudentCardDatabaseHelper(context)
    private val db: SQLiteDatabase = dbHelper.writableDatabase

    fun insertStudentCard(card: StudentCard) {
        val values = ContentValues().apply {
            put("studentName", card.studentName)
            put("studentCourse", card.studentCourse)
            put("studentCicle", card.studentCicle)
            put("studentId", card.studentId)
            put("year", card.year)
            put("primaryLogo", card.primaryLogo)
            put("secondaryLogo", card.secondaryLogo)
            put("profileImage", card.profileImage)
        }
        db.insert("student_cards", null, values)
    }

    fun updateStudentCardById(card: StudentCard): Boolean{
        val contentValues = ContentValues().apply {
            put("studentName", card.studentName)
            put("studentCourse", card.studentCourse)
            put("studentCicle", card.studentCicle)
            put("studentId", card.studentId)
            put("year", card.year)
            put("primaryLogo", card.primaryLogo)
            put("secondaryLogo", card.secondaryLogo)
            put("profileImage", card.profileImage)
        }

        // Atualiza o registro correspondente ao ID fornecido
        val rowsAffected = db.update(
            "student_cards",     // Nome da tabela
            contentValues,       // Valores a serem atualizados
            "studentId = ?",            // Condição para identificar o registro
            arrayOf(card.studentId) // Argumento para a condição
        )

        return rowsAffected > 0 // Retorna true se alguma linha foi atualizada
    }

    fun loadStudentCardFromId(studentId: Long): StudentCard? {
        val cursor = db.query(
            "student_cards",
            null,
            "studentId = ?",
            arrayOf(studentId.toString()),
            null,
            null,
            null
        )

        var studentCard: StudentCard? = null
        if (cursor.moveToFirst()) {
            studentCard = StudentCard(
                studentName = cursor.getString(cursor.getColumnIndexOrThrow("studentName")),
                studentCourse = cursor.getString(cursor.getColumnIndexOrThrow("studentCourse")),
                studentCicle = cursor.getString(cursor.getColumnIndexOrThrow("studentCicle")),
                studentId = cursor.getString(cursor.getColumnIndexOrThrow("studentId")),
                year = cursor.getString(cursor.getColumnIndexOrThrow("year")),
                primaryLogo = cursor.getString(cursor.getColumnIndexOrThrow("primaryLogo")),
                secondaryLogo = cursor.getString(cursor.getColumnIndexOrThrow("secondaryLogo")),
                profileImage = cursor.getString(cursor.getColumnIndexOrThrow("profileImage"))
            )
        }
        cursor.close()
        return studentCard
    }

    fun getAllStudentCards(): List<StudentCard> {
        val studentCards = mutableListOf<StudentCard>()
        val cursor = db.query("student_cards", null, null, null, null, null, null)

        while (cursor.moveToNext()) {
            val card = StudentCard()
                card.studentName = cursor.getString(cursor.getColumnIndexOrThrow("studentName"))
                card.studentCourse = cursor.getString(cursor.getColumnIndexOrThrow("studentCourse"))
                card.studentCicle = cursor.getString(cursor.getColumnIndexOrThrow("studentCicle"))
                card.studentId = cursor.getString(cursor.getColumnIndexOrThrow("studentId"))
                card.year = cursor.getString(cursor.getColumnIndexOrThrow("year"))
                card.primaryLogo = cursor.getString(cursor.getColumnIndexOrThrow("primaryLogo"))
                card.secondaryLogo = cursor.getString(cursor.getColumnIndexOrThrow("secondaryLogo"))
                card.profileImage = cursor.getString(cursor.getColumnIndexOrThrow("profileImage"))
            studentCards.add(card)
        }
        cursor.close()
        return studentCards
    }

    fun deleteStudentCardById(studentId: String, context: Context): Int {
        // Consulta os caminhos das imagens antes de excluir o registro
        val cursor = db.query(
            "student_cards",
            arrayOf("primaryLogo", "secondaryLogo", "profileImage"), // Seleciona os caminhos das imagens
            "studentId = ?",
            arrayOf(studentId),
            null,
            null,
            null
        )

        // Variáveis para armazenar os caminhos das imagens
        var primaryLogoPath: String? = null
        var secondaryLogoPath: String? = null
        var profileImagePath: String? = null

        // Se houver um resultado, obtém os caminhos das imagens
        if (cursor.moveToFirst()) {
            primaryLogoPath = cursor.getString(cursor.getColumnIndexOrThrow("primaryLogo"))
            secondaryLogoPath = cursor.getString(cursor.getColumnIndexOrThrow("secondaryLogo"))
            profileImagePath = cursor.getString(cursor.getColumnIndexOrThrow("profileImage"))
        }
        cursor.close()

        // Exclui os arquivos de imagem, se existirem
        primaryLogoPath?.let { path ->
            val file = File(path)
            if (file.exists()) {
                file.delete()
            }
        }

        secondaryLogoPath?.let { path ->
            val file = File(path)
            if (file.exists()) {
                file.delete()
            }
        }

        profileImagePath?.let { path ->
            val file = File(path)
            if (file.exists()) {
                file.delete()
            }
        }

        // Exclui o registro do banco de dados
        return db.delete(
            "student_cards",
            "studentId = ?",
            arrayOf(studentId)
        )
    }



}