package com.example.practice.models.photo_details


import com.example.practice.models.photo_details.Experimental
import com.example.practice.models.photo_details.TexturesPatterns
import com.google.gson.annotations.SerializedName

data class TopicSubmissions(
    @SerializedName("experimental")
    val experimental: Experimental,
    @SerializedName("textures-patterns")
    val texturesPatterns: TexturesPatterns
)