package com.example.practice.models.photo_search


import com.google.gson.annotations.SerializedName

data class LinksX(
    @SerializedName("html")
    val html: String,
    @SerializedName("likes")
    val likes: String,
    @SerializedName("photos")
    val photos: String,
    @SerializedName("self")
    val self: String
)