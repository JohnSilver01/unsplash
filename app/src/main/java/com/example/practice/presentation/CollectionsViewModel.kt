package com.example.practice.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.practice.data.CollectionPagingSource
import com.example.practice.data.api.UnsplashApi.Companion.PER_PAGE
import com.example.practice.models.collections.CollectionsItem
import com.avv2050soft.unsplashtool.domain.repository.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CollectionsViewModel @Inject constructor(
    private val repository: UnsplashRepository
) : ViewModel() {
    val pageCollections: Flow<PagingData<CollectionsItem>> = Pager(
        config = PagingConfig(pageSize = PER_PAGE),
        pagingSourceFactory = { CollectionPagingSource(repository) }
    ).flow.cachedIn(viewModelScope)
}