package com.amir.testapp.data.dataSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.amir.testapp.data.api.ApiService
import com.amir.testapp.data.db.AppDatabase
import com.amir.testapp.data.model.Content
import com.amir.testapp.data.model.Request
import retrofit2.HttpException
import java.io.IOException

class FavoritePagingSource(val database: AppDatabase) :
    PagingSource<Int, Content>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Content> {

        return try {
            val response = database.contentDao().getAllContents()
            LoadResult.Page(
                data = response.value!!,
                prevKey = null,
                nextKey = null
            )
        } catch (e: IOException) {
            val io = IOException("internet error")
            return LoadResult.Error(io)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Content>): Int? {
        return state.anchorPosition
    }
}