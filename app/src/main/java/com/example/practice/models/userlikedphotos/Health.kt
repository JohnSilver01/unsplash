package com.example.practice.models.userlikedphotos


import com.google.gson.annotations.SerializedName

data class Health(
    @SerializedName("status")
    val status: String
)