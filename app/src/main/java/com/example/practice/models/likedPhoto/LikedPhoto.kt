package com.example.practice.models.likedPhoto


import com.google.gson.annotations.SerializedName

data class LikedPhoto(
    @SerializedName("photo")
    val photo: Photo,
    @SerializedName("user")
    val user: User
)