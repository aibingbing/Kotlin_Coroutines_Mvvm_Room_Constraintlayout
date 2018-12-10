package net.betterbing.kotlincoroutinehelloworld.feature.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity
class Repos : Serializable {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
    @ColumnInfo
    var name: String? = null
    @ColumnInfo
    var full_name: String? = null
    @ColumnInfo
    var html_url: String? = null
}