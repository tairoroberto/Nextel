package com.tairoroberto.nextel.home.model.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.tairoroberto.nextel.home.model.domain.Movie
import io.reactivex.Flowable

/**
 * Created by tairo on 12/12/17 3:03 PM.
 */
@Dao
interface MovieDAO {
    @Query("SELECT * FROM movies")
    fun loadAllMovies(): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<Movie>?)

    @Query("select * from movies where id = :id")
    fun loadMovie(id: Int): Flowable<Movie>

    @Query("select * from movies where id = :id")
    fun loadMovieSync(id: Int): Movie

    @Query("DELETE FROM movies")
    fun deleteAllMovies()
}