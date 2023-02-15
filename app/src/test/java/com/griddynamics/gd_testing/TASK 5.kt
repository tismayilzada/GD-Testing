package com.griddynamics.gd_testing

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.griddynamics.gd_testing.data.local.RepoDao
import com.griddynamics.gd_testing.data.local.RepoDatabase
import com.griddynamics.gd_testing.data.local.dto.RepoDto
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class RepoDatabaseTest{

    private lateinit var database: RepoDatabase
    private lateinit var repoDao: RepoDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, RepoDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        repoDao = database.repoDao
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testInsert() {
        runBlocking {
            val myEntity = RepoDto(1, "My repo")
            repoDao.insertRepo(myEntity)
            val allRepos = repoDao.getRepos().first()
            assertThat(allRepos).contains(myEntity)
        }
    }

    @Test
    fun testDelete() {
        runBlocking {
            val myEntity = RepoDto(1, "My repo")
            repoDao.insertRepo(myEntity)
            repoDao.deleteRepo(myEntity)

            val allNotes = repoDao.getRepos().first()

            assertThat(allNotes).doesNotContain(myEntity)
        }
    }
}