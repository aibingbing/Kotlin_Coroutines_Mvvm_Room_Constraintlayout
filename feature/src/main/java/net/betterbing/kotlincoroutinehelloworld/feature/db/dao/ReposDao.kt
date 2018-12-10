package net.betterbing.kotlincoroutinehelloworld.feature.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import net.betterbing.kotlincoroutinehelloworld.feature.db.entity.Repos

@Dao
interface ReposDao {
    @Query("select * from repos")
    fun getAll(): LiveData<List<Repos>>

    @Insert(onConflict = REPLACE)
    fun addRepos(vararg repos: Repos)

    @Delete
    fun delete(repos: Repos)
}