package com.estudante.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class StudentCardDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE student_cards (
                id INTEGER PRIMARY KEY AUTOINCREMENT, 
                
                studentName TEXT,
                studentCourse TEXT,
                studentCicle TEXT,
                studentId TEXT,
                year TEXT,
                primaryLogo TEXT,
                secondaryLogo TEXT,
                profileImage TEXT
            )
            """
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS student_cards")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "student_cards.db"
        private const val DATABASE_VERSION = 1
    }
}