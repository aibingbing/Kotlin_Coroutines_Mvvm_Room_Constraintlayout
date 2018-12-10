package net.betterbing.kotlincoroutinehelloworld.feature.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity
class User : Serializable {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
    @ColumnInfo
    var login: String? = null
    @ColumnInfo
    var name: String? = null
    @ColumnInfo
    var company: String? = null
    @ColumnInfo
    var location: String? = null
    @ColumnInfo
    var blog: String? = null
    @ColumnInfo
    var followers: Int = 0
    @ColumnInfo
    var following: Int = 0
    @ColumnInfo
    var created_at: String? = null
    @ColumnInfo
    var updated_at: String? = null
    @ColumnInfo
    var migrationText: String? = null
//    @ColumnInfo
//    var migrationText1: String? = null
}
