package com.example.practice.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.practice.models.photos.Photo

@Dao
interface PhotosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(photo: Photo)

    @Query("SELECT * FROM photo_items")
    suspend fun getAllPhotosFromDb(): List<Photo>

    @Query("DELETE FROM photo_items")
    suspend fun deleteAllPhotos()
}