package com.example.practice.models.photo_details


import com.google.gson.annotations.SerializedName

data class ArtsCulture(
    @SerializedName("approved_on")
    val approvedOn: String,
    @SerializedName("status")
    val status: String
)