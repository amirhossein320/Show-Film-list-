package com.amir.testapp.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.versionedparcelable.ParcelField
import com.google.gson.annotations.SerializedName

@Entity
data class Content(
    @SerializedName("ContentID") @PrimaryKey val id: Int,
    @SerializedName("Title") val title: String,
    @SerializedName("Summary") val summary: String,
    @SerializedName("ThumbImage") val thumbImage: String,
    @SerializedName("LandscapeImage") val landscapeImage: String,
    @SerializedName("FavoriteStatus") var isFavorite: Boolean = false,
    @SerializedName("ZoneID")  val _zoneId: Int
) {

    val zone: String?
        get() = when (_zoneId) {
            3 -> "سریال"
            4 -> "فیلم"
            else -> "نامعلوم"
        }

}

