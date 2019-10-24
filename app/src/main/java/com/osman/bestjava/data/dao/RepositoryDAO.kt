package com.osman.bestjava.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.osman.bestjava.data.entity.Repository

@Dao
interface RepositoryDAO {

    @get:Query("SELECT * from repository ORDER BY stargazersCount DESC")
    val repositories: LiveData<List<Repository>>

    @Query("SELECT * FROM repository ORDER BY stargazersCount DESC")
    fun getAll(): List<Repository>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createAll(repositories: List<Repository>)

}