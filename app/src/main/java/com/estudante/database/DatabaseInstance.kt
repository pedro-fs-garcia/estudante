package com.estudante.database

import android.content.Context
import androidx.room.Room

object DatabaseInstance {
    @Volatile
    private var INSTANCE: StudentCardDatabase? = null

    fun getDatabase(context: Context): StudentCardDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                StudentCardDatabase::class.java,
                "student_card_database"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}