package com.amir.testapp.data.model

import com.google.gson.annotations.SerializedName

data class Result<T>(
    @SerializedName("Status") val status: Int,
    @SerializedName("Message") val message: String,
    @SerializedName("Result") val result: T?) {
}