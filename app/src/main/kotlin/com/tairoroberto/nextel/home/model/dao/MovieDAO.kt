package com.tairoroberto.nextel.home.model.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.tairoroberto.nextel.home.model.domain.Movie

/**
 * Created by tairo on 11/12/17 3:03 PM.
 */
@Dao
interface MovieDAO {
    @Query("SELECT * FROM movies")
    abstract fun loadAllMovies(): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(movies: List<Movie>?)

//    @Query("select * from movies where idMovie = :idMovie")
  //  abstract fun loadMovie(idMovie: Int): LiveData<Movie>

   // @Query("select * from movies where idMovie = :idMovie")
    //abstract fun loadMovieSync(idMovie: Int): Movie
}