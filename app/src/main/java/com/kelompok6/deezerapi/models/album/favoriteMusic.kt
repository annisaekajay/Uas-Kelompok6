package com.kelompok6.deezerapi.models.album

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class favoriteMusic(

    @ColumnInfo(name = "duration")
    val duration: Int,
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "md5_image")
    val md5_image: String,
    @ColumnInfo(name = "preview")
    val preview: String,
    @ColumnInfo(name = "title")
    val title: String,

)