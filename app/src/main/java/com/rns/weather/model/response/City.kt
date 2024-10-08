package com.rns.weather.model.response


import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("country")
    val country: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("population")
    val population: Int,
    @SerializedName("timezone")
    val timezone: Int
)