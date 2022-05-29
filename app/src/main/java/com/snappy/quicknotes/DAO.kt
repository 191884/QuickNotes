package com.snappy.quicknotes

import androidx.room.*

@Dao
interface DAO {
    @Insert
    fun insertTask(entity: Entity)

    @Update
    fun updateTask(entity: Entity)

    @Delete
    fun deleteTask(entity: Entity)

    @Query("Delete from Quick_Note")
    fun deleteAll()

    @Query("Select * from Quick_Note ORDER BY priority")
    fun getTasks(): List<CardInfo>

}