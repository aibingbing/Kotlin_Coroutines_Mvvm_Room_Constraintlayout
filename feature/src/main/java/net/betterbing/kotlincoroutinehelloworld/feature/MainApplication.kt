package net.betterbing.kotlincoroutinehelloworld.feature

import android.app.Application
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Room
import android.arch.persistence.room.migration.Migration
import com.facebook.stetho.Stetho
import net.betterbing.kotlincoroutinehelloworld.feature.db.AppDatabase

class MainApplication : Application() {
    companion object {
        val BASE_URL = "https://api.github.com"
        lateinit var db: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE User ADD COLUMN migrationText varchar(200)")
            }
        }
        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "test")
            .fallbackToDestructiveMigration()
            .addMigrations(MIGRATION_1_2)
            .build()
        // init after room build finished
        Stetho.initializeWithDefaults(applicationContext)
    }
}