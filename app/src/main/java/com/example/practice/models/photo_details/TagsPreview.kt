package com.example.practice.models.photo_details


import com.google.gson.annotations.SerializedName

data class TagsPreview(
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String
)