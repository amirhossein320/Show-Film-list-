package com.amir.testapp.di

import android.content.Context
import com.amir.testapp.data.api.ApiService
import com.amir.testapp.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun appDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Provides
    fun retrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://core.gapfilm.ir/mobile/request.asmx/")
        .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun ApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}