package com.example.practice.models.collections


import com.google.gson.annotations.SerializedName

data class Experimental(
    @SerializedName("status")
    val status: String
)