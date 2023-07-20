package com.rns.weather.model.network

import com.google.gson.Gson
import com.rns.weather.model.response.WeatherResponse
import com.rns.weather.util.Constant.API_KEY
import com.rns.weather.util.Constant.APP_ID
import com.rns.weather.util.Constant.HOST
import com.rns.weather.util.Constant.PATH_SEGMENTS
import com.rns.weather.util.Constant.Q
import com.rns.weather.util.Constant.SCHEME
import com.rns.weather.util.Constant.UNITS
import com.rns.weather.util.State
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request

class Client {
    private val client = OkHttpClient()

    fun fetchWeatherData(): State<WeatherResponse> {
        val request = Request.Builder().url(getWeatherUrl()).build()
        val response = client.newCall(request).execute()

        return if (response.isSuccessful) {
            val result = Gson().fromJson(response.body?.string(), WeatherResponse::class.java)
            State.Success(result)
        } else {
            State.Error(response.message)
        }
    }

    private fun getWeatherUrl(): HttpUrl {
        return HttpUrl.Builder()
            .scheme(SCHEME)
            .host(HOST)
            .addPathSegment(PATH_SEGMENTS)
            .addQueryParameter(Q, "palestine")
            .addQueryParameter(APP_ID, API_KEY)
            .addQueryParameter(UNITS, "metric")
            .build()
    }
}