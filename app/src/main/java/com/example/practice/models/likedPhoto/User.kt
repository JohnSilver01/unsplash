package com.example.practice.models.likedPhoto


import com.example.practice.models.likedPhoto.LinksX
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: String,
    @SerializedName("links")
    val links: LinksX,
    @SerializedName("name")
    val name: String,
    @SerializedName("username")
    val username: String
)