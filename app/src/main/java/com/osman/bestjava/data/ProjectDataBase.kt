package com.osman.bestjava.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.osman.bestjava.data.converter.OwnerConverter
import com.osman.bestjava.data.dao.PullRequestDAO
import com.osman.bestjava.data.dao.RepositoryDAO
import com.osman.bestjava.data.entity.PullRequest
import com.osman.bestjava.data.entity.Repository

@Database(
    entities = [Repository::class, PullRequest::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(OwnerConverter::class)
abstract class ProjectDataBase : RoomDatabase() {

    abstract fun repositoryDAO(): RepositoryDAO
    abstract fun pullRequestDAO(): PullRequestDAO

    companion object {

        @Volatile
        private var INSTANCE: ProjectDataBase? = null

        internal fun getDatabase(context: Context): ProjectDataBase? {
            if (INSTANCE == null) {
                synchronized(ProjectDataBase::class) {
                    INSTANCE =
                        Room.databaseBuilder(
                            context.applicationContext,
                            ProjectDataBase::class.java,
                            "project_database"
                        )
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build()
                }
            }
            return INSTANCE
        }
    }

    fun destroyDataBase() {
        INSTANCE = null
    }

}