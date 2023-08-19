package com.example.practice.models.collectioninfo


import com.google.gson.annotations.SerializedName

data class Meta(
    @SerializedName("description")
    val description: Any,
    @SerializedName("index")
    val index: Boolean,
    @SerializedName("title")
    val title: Any
)