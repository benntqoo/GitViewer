package com.jrtou.gitviewer.databases

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jrtou.gitviewer.databases.dao.TrendDao
import com.jrtou.gitviewer.databases.schemas.TrendSchema

@Database(entities = [TrendSchema::class], version = 1)
abstract class DatabaseHelper : RoomDatabase() {
    companion object {
        private const val TAG = "DatabaseHelper"
        private const val databaseName = "GitViewer"

        lateinit var instance: DatabaseHelper

        fun init(context: Context) {
            Log.i(TAG, "init (line 19): ")
            synchronized(DatabaseHelper::class) {
                instance = Room.databaseBuilder(context.applicationContext, DatabaseHelper::class.java, databaseName)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
        }
    }

    abstract fun getTrendDao(): TrendDao
}