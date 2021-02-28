package com.amir.testapp.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.amir.testapp.data.api.ApiService
import com.amir.testapp.data.dataSource.ContentPagingSource
import com.amir.testapp.data.db.AppDatabase
import com.amir.testapp.data.model.Content
import com.amir.testapp.data.model.Request
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ContentViewModel @Inject constructor(private val apiService: ApiService, private val database: AppDatabase) :
    ViewModel() {

    private val contents = MutableLiveData<PagingData<Content>>()
    val content = MutableLiveData<Content>()

    private val pagingContents = Pager(PagingConfig(pageSize = 10)) {
        ContentPagingSource(apiService, Request(Request.RequestRaw(pageIndex = 1)))
    }.flow
        .cachedIn(viewModelScope)

    fun fetchContents() {
        viewModelScope.launch {

            try {
                pagingContents.collectLatest {
                    contents.postValue(it)
                }

            } catch (e: Exception) {
                Log.e("TAG", "fetchContents: ${e.message}")
            }
        }
    }


    fun fetchContent(id: Int) {
        viewModelScope.launch {

            try {
                val fetchedContent =
                    apiService.getContent(Request(Request.RequestRaw(requestId = id)))

                with(fetchedContent) {
                    if (isSuccessful) {
                        content.postValue(body()?.result!!)
                    } else {
                        Log.e("TAG", "fetchContent: ${message()}")
                    }
                }

            } catch (e: Exception) {
                Log.e("TAG", "fetchContent: ${e.message}")
            }
        }
    }


    fun getContents(): LiveData<PagingData<Content>> {
        return contents
    }

    fun getFavoriteContents(): LiveData<List<Content>> {
        return database.contentDao().getAllContents()
    }

    fun insertFavorite(content: Content) {
        viewModelScope.launch {
            database.contentDao().insert(content)
        }
    }

    fun deleteFavorite(content: Content) {
        viewModelScope.launch {
            database.contentDao().delete(content)
        }
    }


}