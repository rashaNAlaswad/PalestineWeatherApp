package com.rns.weather.repository

import io.reactivex.rxjava3.core.Observable
import com.rns.weather.model.network.Client
import com.rns.weather.model.response.WeatherResponse
import com.rns.weather.util.State

class WeatherRepository {
    private val client = Client()

    fun getWeather(): Observable<State<WeatherResponse>> =
        Observable.create { emitter ->
            emitter.onNext(State.Loading)
            emitter.onNext(client.fetchWeatherData())
        }
}