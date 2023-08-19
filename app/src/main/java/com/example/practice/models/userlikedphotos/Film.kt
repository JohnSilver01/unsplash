package com.example.practice.models.userlikedphotos


import com.google.gson.annotations.SerializedName

data class Film(
    @SerializedName("status")
    val status: String
)