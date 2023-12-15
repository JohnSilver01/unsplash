package com.example.practice.data.repository

import android.content.Context
import com.example.practice.data.db.UnsplashDatabase
import com.example.practice.models.photos.Photo
import com.avv2050soft.unsplashtool.domain.repository.DatabaseRepository
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    context: Context
) : DatabaseRepository {

    private val db = UnsplashDatabase.getInstance(context)

    override suspend fun insert(photo: Photo) {
        db.photosDao().insert(photo)
    }

    override suspend fun getAllPhotosFromDb(): List<Photo> {
        return db.photosDao().getAllPhotosFromDb()
    }

    override suspend fun deleteAllPhotosFromDb() {
        db.photosDao().deleteAllPhotos()
    }
    /
}