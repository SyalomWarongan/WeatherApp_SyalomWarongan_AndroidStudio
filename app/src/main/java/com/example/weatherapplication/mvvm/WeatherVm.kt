package com.example.weatherapplication.mvvm

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapplication.*
import com.example.weatherapplication.service.RetrofitInstance
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.weatherapplication.WeatherList
import kotlin.collections.ArrayList

class WeatherVm : ViewModel() {

    val todayWeatherLiveData = MutableLiveData<ArrayList<WeatherList>>()
    val forecastWeatherLiveData = MutableLiveData<ArrayList<WeatherList>>()

    val closetorexactlysameweatherdata = MutableLiveData<WeatherList?>()
    val cityName = MutableLiveData<String>()
    val context = MyApp.instance


    @RequiresApi(Build.VERSION_CODES.O)
    fun getWeather(city: String? = null) = viewModelScope.launch(Dispatchers.IO){
        val todayWeatherList = mutableListOf<WeatherList>()

        val currentDateTime = LocalDateTime.now()
        val currentDatePattern = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        val sharedPref = SharedPref.getInstance(context)
        val lat = sharedPref.getValue("lat").toString()
        val lon = sharedPref.getValue("lon").toString()

        Log.e("COOR", "$lat $lon")

        val call = if (city!=null){
            RetrofitInstance.api.getWeatherByCity(city)
        }else {
            RetrofitInstance.api.getCurrentWeather(lat, lon)
        }

        val response = call.execute()

        if(response.isSuccessful) {
            val weatherList = response.body()?.weatherList
            cityName.postValue(response.body()?.city!!.name)

            val presentDate = currentDatePattern

            weatherList?.forEach{ weather: WeatherList ->
                if (weather.dtTxt!!.split("\\s".toRegex()).contains(presentDate)) {
                    todayWeatherList.add(weather)
                }
            }

            val closetWeather = findClosetWeather(todayWeatherList)
            closetorexactlysameweatherdata.postValue(closetWeather)

            todayWeatherLiveData.postValue(todayWeatherList as ArrayList<WeatherList>?)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getForecastUpcoming(city: String? = null) = viewModelScope.launch(Dispatchers.IO){
        val forecastWeatherList = mutableListOf<WeatherList>()

        val currentDateTime = LocalDateTime.now()
        val currentDatePattern = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        val sharedPref = SharedPref.getInstance(context)
        val lat = sharedPref.getValue("lat").toString()
        val lon = sharedPref.getValue("lon").toString()

        val call = if (city!=null){
            RetrofitInstance.api.getWeatherByCity(city)
        }else {
            RetrofitInstance.api.getCurrentWeather(lat, lon)
        }

        val response = call.execute()

        if(response.isSuccessful) {
            val weatherList = response.body()?.weatherList
            cityName.postValue(response.body()?.city!!.name)

            val presentDate = currentDatePattern

            weatherList?.forEach{ weather: WeatherList ->
                if (!weather.dtTxt!!.split("\\s".toRegex()).contains(presentDate)) {

                    if (weather.dtTxt!!.substring(11, 16) == "12:00") {
                        forecastWeatherList.add(weather)


                    }
                }
            }

            forecastWeatherLiveData.postValue(forecastWeatherList as ArrayList<WeatherList>)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun findClosetWeather(weatherList: MutableList<WeatherList>): WeatherList? {
        val systemTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
        var closestWeather: WeatherList? = null
        var minTimeDifference = Int.MAX_VALUE

        for (weather in weatherList) {
            val weatherTime = weather.dtTxt!!.substring(11, 16)
            val timeDifference = Math.abs(timeToMinutes(weatherTime) - timeToMinutes(systemTime))

            if (timeDifference < minTimeDifference) {
                minTimeDifference = timeDifference
                closestWeather = weather
            }
        }

        return closestWeather
    }

    private fun timeToMinutes(time: String): Int {
        val parts = time.split(":")
        return parts[0].toInt() * 60 + parts[1].toInt()
    }
}