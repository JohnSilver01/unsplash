package com.example.practice.models.collectioninfo


import com.google.gson.annotations.SerializedName

data class CollectionInfo(
    @SerializedName("cover_photo")
    val coverPhoto: com.example.practice.models.collectioninfo.CoverPhoto,
    @SerializedName("curated")
    val curated: Boolean,
    @SerializedName("description")
    val description: String,
    @SerializedName("featured")
    val featured: Boolean,
    @SerializedName("id")
    val id: String,
    @SerializedName("last_collected_at")
    val lastCollectedAt: String,
    @SerializedName("links")
    val links: com.example.practice.models.collectioninfo.LinksX,
    @SerializedName("meta")
    val meta: com.example.practice.models.collectioninfo.Meta,
    @SerializedName("private")
    val `private`: Boolean,
    @SerializedName("published_at")
    val publishedAt: String,
    @SerializedName("share_key")
    val shareKey: String,
    @SerializedName("tags")
    val tags: List<com.example.practice.models.collectioninfo.Tag>,
    @SerializedName("title")
    val title: String,
    @SerializedName("total_photos")
    val totalPhotos: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user")
    val user: com.example.practice.models.collectioninfo.User
)