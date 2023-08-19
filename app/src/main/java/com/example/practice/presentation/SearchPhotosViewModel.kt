package com.example.practice.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.practice.data.SearchPhotoPagingSource
import com.example.practice.data.api.UnsplashApi
import com.example.practice.models.photo_search.Result
import com.avv2050soft.unsplashtool.domain.repository.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchPhotosViewModel @Inject constructor(
    repository: UnsplashRepository
) : ViewModel() {

    val searchPhotos: Flow<PagingData<Result>> = Pager(
        config = PagingConfig(pageSize = UnsplashApi.PER_PAGE),
        pagingSourceFactory = { SearchPhotoPagingSource(repository) }
    ).flow.cachedIn(viewModelScope)

    companion object{
        var query: String = "jeans"
    }
}