package com.example.practice.models.collectioninfo


import com.google.gson.annotations.SerializedName

data class Tag(
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String
)