package com.tairoroberto.nextel.home.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context

import com.tairoroberto.nextel.base.converter.DateConverter
import com.tairoroberto.nextel.home.model.dao.MovieDAO
import com.tairoroberto.nextel.home.model.domain.Movie

@Database(entities = arrayOf(Movie::class), version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDAO(): MovieDAO

    companion object {

        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context?): AppDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context as Context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "nextel.db")
                        .build()
    }
}
