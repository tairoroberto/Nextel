package com.tairoroberto.nextel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.tairoroberto.nextel.home.model.AppDatabase
import com.tairoroberto.nextel.home.model.domain.Movie
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MovieDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase

    @Before
    fun initDb() {
        // using an in-memory database because the information stored here disappears after test
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                AppDatabase::class.java)
                .allowMainThreadQueries()
                .build()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun getMoviesWhenNoMovieInserted() {
        database.movieDAO().loadMovie(123)
                .test()
                .assertNoValues()
    }

    @Test
    fun insertAndGetMovie() {
        database.movieDAO().insertAll(arrayListOf(MOVIE))

        database.movieDAO().loadMovie(MOVIE.idMovie)
                .test()
                .assertValue { it.idMovie == MOVIE.idMovie && it.title == MOVIE.title }
    }

    @Test
    fun updateAndGetMovie() {
        database.movieDAO().insertAll(arrayListOf(MOVIE))

        MOVIE.title = "new movie title"

        database.movieDAO().insertAll(arrayListOf(MOVIE))
        database.movieDAO().loadMovie(MOVIE.idMovie)
                .test()
                .assertValue { it.idMovie == MOVIE.idMovie && it.title == "new movie title" }
    }

    @Test
    fun deleteAndGetMovie() {
        database.movieDAO().insertAll(arrayListOf(MOVIE))
        database.movieDAO().deleteAllMovies()
        database.movieDAO().loadMovie(MOVIE.idMovie)
                .test()
                .assertNoValues()
    }

    companion object {
        private val MOVIE = Movie(
                "Thor is imprisoned on the other side of the universe and finds himself in a race against time to get back to Asgard to stop Ragnarok, the destruction of his homeworld and the end of Asgardian civilization, at the hands of an all-powerful new threat, the ruthless Hela.",
                "en",
                "Thor: Ragnarok",
                false,
                "Thor: Ragnarok",
                "/oSLd5GYGsiGgzDPKTwQh7wamO8t.jpg",
                "/wBzMnQ01R9w58W6ucltdYfOyP4j.jpg",
                "2017-10-25",
                7.7,
                812.412124,
                284053,
                false,
                1352)
    }
}
