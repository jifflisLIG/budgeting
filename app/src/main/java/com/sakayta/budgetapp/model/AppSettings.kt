package com.sakayta.budgetapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "app_settings")
class AppSettings(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int = 0,
    @ColumnInfo(name =  "is_fisrt_install")
    var is_fisrt_install:Boolean = true
) : Parcelable {
    override fun toString(): String {
        return "AppSettings(id=$id, refresh_date='$is_fisrt_install')"
    }
}