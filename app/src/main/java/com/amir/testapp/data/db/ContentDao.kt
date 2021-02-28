package com.amir.testapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.amir.testapp.data.model.Content

@Dao
interface ContentDao {

    @Query("SELECT * FROM content")
    fun getAllContents(): LiveData<List<Content>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(content: Content)

    @Delete
    suspend fun delete(content: Content)
}