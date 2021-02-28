package com.amir.testapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Contents(
    @SerializedName("GetContentList") val contents: List<Content>,
    val totalPages:Int
) {

}

