package com.jrtou.gitviewer.databases.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jrtou.gitviewer.databases.schemas.TrendSchema

@Dao
interface TrendDao {
    @Insert
    fun insert(trendItem: TrendSchema)

    @Query("SELECT * FROM " + TrendSchema.tableName)
    fun getALl(): LiveData<MutableList<TrendSchema>>

    @Query("DELETE FROM " + TrendSchema.tableName)
    fun deleteAll()
}