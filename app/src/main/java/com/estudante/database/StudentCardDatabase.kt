package com.estudante.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.estudante.dao.StudentCardDao
import com.estudante.models.StudentCardEntity

@Database(entities = [StudentCardEntity::class], version = 1, exportSchema = false)
abstract class StudentCardDatabase : RoomDatabase() {
    abstract fun studentCardDao(): StudentCardDao
}
