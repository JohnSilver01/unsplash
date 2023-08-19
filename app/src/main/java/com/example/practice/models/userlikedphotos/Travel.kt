package com.example.practice.models.userlikedphotos


import com.google.gson.annotations.SerializedName

data class Travel(
    @SerializedName("approved_on")
    val approvedOn: String,
    @SerializedName("status")
    val status: String
)