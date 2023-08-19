package com.example.practice.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.practice.data.CollectionPhotoItemPagingSource
import com.example.practice.data.api.UnsplashApi.Companion.PER_PAGE
import com.example.practice.models.collectionphotos.CollectionPhotoItem
import com.avv2050soft.unsplashtool.domain.repository.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CollectionDetailsViewModel @Inject constructor(
    private val unsplashRepository: UnsplashRepository
) : ViewModel() {
    private var collectionInfo: com.example.practice.models.collectioninfo.CollectionInfo? = null
    private val _collectionInfoStateFlow = MutableStateFlow(collectionInfo)
    val collectionInfoStateFlow = _collectionInfoStateFlow

    val thisCollectionPhotos: Flow<PagingData<CollectionPhotoItem>> = Pager(
        config = PagingConfig(pageSize = PER_PAGE),
        pagingSourceFactory = { CollectionPhotoItemPagingSource (unsplashRepository) }
    ).flow.cachedIn(viewModelScope)

    fun getThisCollectionInfo(id: String) {
        viewModelScope.launch {
            try {
                collectionInfo = unsplashRepository.getThisCollectionInfo(id)
                _collectionInfoStateFlow.value = collectionInfo
                Log.d("data_test", "CollectionPhotoInfoC: ${collectionInfo.toString()}")
            } catch (e: Exception) {
                Log.d("data_test", "Error in VM: ${e.message.toString()}")
            }
        }
    }

    companion object{
        var collectionId: String = "1dTgp8ML_DY"
    }
}