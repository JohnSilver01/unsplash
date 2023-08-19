package com.example.practice.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.practice.models.photo_search.Result
import com.avv2050soft.unsplashtool.domain.repository.UnsplashRepository
import com.example.practice.presentation.SearchPhotosViewModel
import javax.inject.Inject

class SearchPhotoPagingSource @Inject constructor(
    private val repository: UnsplashRepository
) : PagingSource<Int, Result>(){
    override fun getRefreshKey(state: PagingState<Int, Result>): Int = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            repository.searchPhotos(page, SearchPhotosViewModel.query)
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else page + 1
                )
            },
            onFailure = {LoadResult.Error(it)}
        )
    }

    companion object {
        private const val FIRST_PAGE = 1
    }
}