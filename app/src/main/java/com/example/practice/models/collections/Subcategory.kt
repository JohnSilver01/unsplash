package com.example.practice.models.collections


import com.google.gson.annotations.SerializedName

data class Subcategory(
    @SerializedName("pretty_slug")
    val prettySlug: String,
    @SerializedName("slug")
    val slug: String
)