package com.geekmk.newsapp.db

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.geekmk.newsapp.data.source.local.Database
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
abstract class DbTest {
  lateinit var db: Database

  @Before
  fun initDB() {
    db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().context,
        Database::class.java).build()
  }

  @After
  fun closeDB() {
    db.close()
  }
}
