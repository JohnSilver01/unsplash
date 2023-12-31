package com.example.practice.models.photo_details


import com.google.gson.annotations.SerializedName

data class PreviewPhoto(
    @SerializedName("blur_hash")
    val blurHash: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("urls")
    val urls: Urls
)