package com.osman.bestjava

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.osman.bestjava.data.ProjectDataBase
import com.osman.bestjava.data.dao.RepositoryDAO
import com.osman.bestjava.data.entity.Owner
import com.osman.bestjava.data.entity.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.IOException

@RunWith(RobolectricTestRunner::class)
class RepositoryUnitTest {

    val context: Context = ApplicationProvider.getApplicationContext<Context>()
    private lateinit var dao: RepositoryDAO
    private lateinit var db: ProjectDataBase

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            context, ProjectDataBase::class.java
        ).build()
        dao = db.repositoryDAO()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {
        val owner1 = Owner(1, "url photo", "name owner")
        val repos = arrayListOf<Repository>()
        repos.add(Repository(1, "repository name1", "Description 1", 789, 100, owner1))
        repos.add(Repository(2, "repository name2", "Description 1", 456, 200, owner1))
        repos.add(Repository(3, "repository name3", "Description 1", 123, 300, owner1))
        runBlocking(Dispatchers.IO) {
            dao.createAll(repos)
            val byName: ArrayList<Repository> = dao.getAll() as ArrayList<Repository>
            assertThat(byName, equalTo(repos))
        }
    }
}