package com.example.practice.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.practice.data.PhotoPagingSource
import com.example.practice.data.api.UnsplashApi.Companion.PER_PAGE
import com.example.practice.models.photos.Photo
import com.avv2050soft.unsplashtool.domain.repository.DatabaseRepository
import com.avv2050soft.unsplashtool.domain.repository.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val repository: UnsplashRepository
) : ViewModel() {
    val pagePhotos: Flow<PagingData<Photo>> = Pager(
        config = PagingConfig(pageSize = PER_PAGE),
        pagingSourceFactory = { PhotoPagingSource(repository) }
    ).flow.cachedIn(viewModelScope)

    private val _photosFromDb = MutableStateFlow(emptyList<Photo>())
    val photosFromDbStateFlow = _photosFromDb.asStateFlow()

    suspend fun insertPhotoInDatabase(photo: Photo) {
        databaseRepository.insert(photo)
    }

    suspend fun loadAllPhotosFromDb() {
        viewModelScope.launch {
            try {
                _photosFromDb.value = databaseRepository.getAllPhotosFromDb()
            } catch (e: Exception) {
                Log.d("data_test", "error ViewModel - ${e.message}")
            }
        }

    }
}