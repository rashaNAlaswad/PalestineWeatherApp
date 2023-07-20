package com.rns.weather.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.rns.weather.R
import com.rns.weather.databinding.ActivityMainBinding
import com.rns.weather.model.response.WeatherResponse
import com.rns.weather.repository.WeatherRepository
import com.rns.weather.ui.adapter.WeatherRecyclerView
import com.rns.weather.util.State
import com.rns.weather.util.add
import com.rns.weather.util.formatDecimals
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private var compositeDisposable = CompositeDisposable()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        subscribeWeather()

        binding.buttonTryAgain.setOnClickListener {
            subscribeWeather()
        }
    }

    private fun subscribeWeather() {
        WeatherRepository().getWeather()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { state ->
                    setResponseState(state)
                },
                {
                    onError()
                }
            ).add(compositeDisposable)
    }

    private fun setResponseState(responseState: State<WeatherResponse>) = when (responseState) {
        is State.Loading -> onLoading()
        is State.Success -> onSuccess(responseData = responseState.data)
        is State.Error -> onError()
    }

    private fun onLoading() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            buttonTryAgain.visibility = View.GONE
        }
    }

    private fun onSuccess(responseData: WeatherResponse?) {
        binding.apply {
            progressBar.visibility = View.GONE
            constraintLayout.visibility = View.VISIBLE
            buttonTryAgain.visibility = View.GONE
        }
        responseData?.let { setViews(it) }
    }

    @SuppressLint("SetTextI18n")
    private fun setViews(weather: WeatherResponse) {
        val weatherItem = weather.list[0]
        val imageUrl = "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}.png"

        binding.apply {
            textLocationName.text = weather.city.name
            textTemperature.text = "${formatDecimals(weatherItem.temp.day)}º"
            textDescription.text = weatherItem.weather[0].main
            Glide.with(applicationContext)
                .load(imageUrl)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(binding.imageWeather)

            textWindSpeed.text = "${formatDecimals(weatherItem.speed)} m/s"
            textHumidity.text = "${weatherItem.humidity}%"
            textRain.text = "${100 * (weatherItem.pop)}%"

            recyclerview.adapter = WeatherRecyclerView(weather.list)
        }
    }


    private fun onError() {
        binding.apply {
            progressBar.visibility = View.GONE
            constraintLayout.visibility = View.GONE
            buttonTryAgain.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }
}