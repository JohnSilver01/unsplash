package com.example.practice.models.photo_details


import com.google.gson.annotations.SerializedName

data class Position(
    @SerializedName("latitude")
    val latitude: Any,
    @SerializedName("longitude")
    val longitude: Any
)