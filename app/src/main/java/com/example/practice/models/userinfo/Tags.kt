package com.example.practice.models.userinfo


import com.google.gson.annotations.SerializedName

data class Tags(
    @SerializedName("aggregated")
    val aggregated: List<Any>,
    @SerializedName("custom")
    val custom: List<Any>
)