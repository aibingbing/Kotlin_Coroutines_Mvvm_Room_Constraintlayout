package net.betterbing.kotlincoroutinehelloworld.feature.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import net.betterbing.kotlincoroutinehelloworld.feature.db.dao.ReposDao
import net.betterbing.kotlincoroutinehelloworld.feature.db.dao.UserDao
import net.betterbing.kotlincoroutinehelloworld.feature.db.entity.Repos
import net.betterbing.kotlincoroutinehelloworld.feature.db.entity.User

@Database(entities = [User::class, Repos::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun reposDao(): ReposDao

//    private var INSTANCE: AppDatabase? = null
//
//    fun getInMemoryDatabase(context: Context): AppDatabase {
//        if (INSTANCE == null) {
//            INSTANCE = Room.inMemoryDatabaseBuilder(context.applicationContext, AppDatabase::class.java)
//                // To simplify the codelab, allow queries on the main thread.
//                // Don't do this on a real app! See PersistenceBasicSample for an example.
////                .allowMainThreadQueries()
//                .build()
//        }
//        return INSTANCE as AppDatabase
//    }
//
//    fun destroyInstance() {
//        INSTANCE = null
//    }
}