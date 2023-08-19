package com.avv2050soft.unsplashtool.domain.repository

import com.example.practice.models.collectionphotos.CollectionPhotoItem
import com.example.practice.models.collections.CollectionsItem
import com.example.practice.models.likedPhoto.LikedPhoto
import com.example.practice.models.photo_details.PhotoDetails
import com.example.practice.models.photo_search.Result
import com.example.practice.models.photos.Photo
import com.example.practice.models.userinfo.CurrentUserInfo
import com.example.practice.models.userlikedphotos.UserLikedPhotoItem

interface UnsplashRepository {

    suspend fun getPhotos(page : Int) : List<Photo>
    suspend fun getPhotoDetails(id : String): PhotoDetails
    suspend fun likePhoto(id: String): LikedPhoto
    suspend fun unlikePhoto(id: String): LikedPhoto
    suspend fun searchPhotos(page: Int, query: String) : List<Result>
    suspend fun getCollection(page: Int): List<CollectionsItem>
    suspend fun getThisCollectionInfo(id: String) : com.example.practice.models.collectioninfo.CollectionInfo
    suspend fun getThisCollectionPhotos(page: Int, id: String) : List<CollectionPhotoItem>
    suspend fun getCurrentUserInfo() : CurrentUserInfo
    suspend fun getUserLikedPhotos(username : String, page: Int) : List<UserLikedPhotoItem>
}