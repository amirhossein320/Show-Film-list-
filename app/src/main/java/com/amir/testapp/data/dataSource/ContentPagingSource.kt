package com.amir.testapp.data.dataSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.amir.testapp.data.api.ApiService
import com.amir.testapp.data.model.Content
import com.amir.testapp.data.model.Request
import retrofit2.HttpException
import java.io.IOException

class ContentPagingSource(val apiService: ApiService, val request: Request) :
    PagingSource<Int, Content>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Content> {

        return try {
            val page = params.key ?: 1
            val response = apiService.getContents(request.apply {
                request.pageIndex = page
            })
            LoadResult.Page(
                data = response.body()?.result?.contents!!,
                prevKey = null,
                nextKey = if (response.body()?.result?.totalPages == page) null else page + 1
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