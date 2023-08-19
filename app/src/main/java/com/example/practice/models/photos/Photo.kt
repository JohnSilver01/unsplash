package com.example.practice.models.photos


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.avv2050soft.unsplashtool.domain.utils.Converter
import com.google.gson.annotations.SerializedName

@Entity(tableName = "photo_items")
@TypeConverters(Converter::class)
data class Photo(
    @SerializedName("alt_description")
    val altDescription: String?,
    @SerializedName("blur_hash")
    val blurHash: String?,
    @SerializedName("color")
    val color: String?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("height")
    val height: Int,

    @PrimaryKey
    @SerializedName("id")
    val id: String,

    @SerializedName("liked_by_user")
    val likedByUser: Boolean,
    @SerializedName("likes")
    val likes: Int,
    @SerializedName("links")
    val links: Links,
    @SerializedName("promoted_at")
    val promotedAt: String?,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("urls")
    val urls: Urls,
    @SerializedName("user")
    val user: User,
    @SerializedName("width")
    val width: Int
)