package com.example.practice.models.photo_details


import com.example.practice.models.photo_details.Source
import com.google.gson.annotations.SerializedName

data class Tag(
    @SerializedName("source")
    val source: Source,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String
)