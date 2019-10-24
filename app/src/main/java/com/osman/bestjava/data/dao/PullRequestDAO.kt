package com.osman.bestjava.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.osman.bestjava.data.entity.PullRequest

@Dao
interface PullRequestDAO {

    @get:Query("SELECT * from repository ORDER BY stargazersCount DESC")
    val pullRequests: LiveData<List<PullRequest>>

    @Query("SELECT * FROM repository ORDER BY stargazersCount DESC")
    fun getAll(): List<PullRequest>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createAll(repositories: List<PullRequest>)

}