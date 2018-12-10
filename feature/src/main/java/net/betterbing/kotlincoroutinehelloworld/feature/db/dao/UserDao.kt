package net.betterbing.kotlincoroutinehelloworld.feature.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import net.betterbing.kotlincoroutinehelloworld.feature.db.entity.User

@Dao
interface UserDao {
    @Query("select * from user")
    fun getAll(): LiveData<List<User>>

    @Query("select * from user where id in (:userIds)")
    fun getUserByIds(userIds: IntArray):List<User>

    @Insert(onConflict = REPLACE)
    fun addUser(vararg users: User)

    @Delete
    fun delete(user: User)
}