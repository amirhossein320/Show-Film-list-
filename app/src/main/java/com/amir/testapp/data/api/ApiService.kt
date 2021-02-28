package com.amir.testapp.data.api

import com.amir.testapp.data.model.Content
import com.amir.testapp.data.model.Contents
import com.amir.testapp.data.model.Request
import com.amir.testapp.data.model.Result
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("GetContentList")
    suspend fun getContents(@Body request: Request): Response<Result<Contents>>


    @POST("GetContent")
    @Headers("Content-Type: application/json", "Accept-Language: fa-IR")
    suspend fun getContent(@Body request: Request): Response<Result<Content>>
}