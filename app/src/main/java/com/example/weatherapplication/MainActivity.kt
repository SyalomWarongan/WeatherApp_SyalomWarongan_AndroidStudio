package com.example.weatherapplication

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapplication.adapter.WeatherToday
import com.example.weatherapplication.databinding.ActivityMainBinding
import com.example.weatherapplication.mvvm.WeatherVm
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var vm : WeatherVm
    private lateinit var adapter : WeatherToday

    @RequiresApi(64)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        vm = ViewModelProvider(this).get(WeatherVm::class.java)

        adapter = WeatherToday()

        binding.lifecycleOwner = this
        binding.vm = vm

        val sharedPref = SharedPref.getInstance(this@MainActivity)
        sharedPref.clearCityValue()

        vm.getWeather()

        vm.todayWeatherLiveData.observe(this, Observer {

            val setNewlist = it as List<WeatherList>

            adapter.setList(setNewlist)
            binding.forecastRecyclerView.adapter = adapter

        })

        vm.closetorexactlysameweatherdata.observe(this, Observer {


            val temperatureFahrenheit = it!!.main?.temp
            val temperatureCelsius = (temperatureFahrenheit?.minus(273.15))
            val temperatureFormatted = String.format("%.2f", temperatureCelsius)

            for (i in it.weather) {

                binding.descMain.text = i.description

            }

            binding.tempMain.text = "$temperatureFormatted°"


            binding.humidityMain.text = it.main!!.humidity.toString()
            binding.windSpeed.text = it.wind?.speed.toString()


            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            val date = inputFormat.parse(it.dtTxt!!)
            val outputFormat = SimpleDateFormat("d MMMM EEEE", Locale.getDefault())
            val dateanddayname = outputFormat.format(date!!)

            binding.dateDayMain.text = dateanddayname

            binding.chanceofrain.text = "${it.pop.toString()}%"


            // setting the icon
            for (i in it.weather) {


                if (i.icon == "01d") {


                    binding.imageMain.setImageResource(R.drawable.sun)

                }

                if (i.icon == "01n") {
                    binding.imageMain.setImageResource(R.drawable.moon)


                }

                if (i.icon == "02d") {

                    binding.imageMain.setImageResource(R.drawable.cloudsandsun)


                }


                if (i.icon == "02n") {
                    binding.imageMain.setImageResource(R.drawable.night)


                }


                if (i.icon == "03d" || i.icon == "03n") {


                    binding.imageMain.setImageResource(R.drawable.threecloud)


                }



                if (i.icon == "10d") {

                    binding.imageMain.setImageResource(R.drawable.rainsun)


                }


                if (i.icon == "10n") {

                    binding.imageMain.setImageResource(R.drawable.raincloud)


                }


                if (i.icon == "04d" || i.icon == "04n") {


                    binding.imageMain.setImageResource(R.drawable.clouds)


                }


                if (i.icon == "09d" || i.icon == "09n") {


                    binding.imageMain.setImageResource(R.drawable.stormcloud)


                }



                if (i.icon == "11d" || i.icon == "11n") {


                    binding.imageMain.setImageResource(R.drawable.thunderstorm)


                }


                if (i.icon == "13d" || i.icon == "13n") {

                    binding.imageMain.setImageResource(R.drawable.snowfall)


                }

                if (i.icon == "50d" || i.icon == "50n") {


                    binding.imageMain.setImageResource(R.drawable.wave)


                }

            }

        })

        if (checkLocationPermissions()) {
            getCurrentLocation()
        } else {
            requestLocationPermissions()
        }

        val searchEditText = binding.searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        (searchEditText.setTextColor(Color.WHITE))

        binding.next5Days.setOnClickListener {
            startActivity(Intent(this, ForecastAct::class.java))
        }

        binding.searchView.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {

                val pref = SharedPref.getInstance(this@MainActivity)
                pref.setValueOrNull("city", query!!)

                if (!query.isNullOrEmpty()) {

                    vm.getWeather(query)

                    binding.searchView.setQuery("", false)
                    binding.searchView.clearFocus()
                    binding.searchView.isIconified = true

                }

                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return true

            }

        })

    }

    private fun checkLocationPermissions() : Boolean{

        val fineLocationPermission = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
        val coarseLocationPermission = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return fineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                coarseLocationPermission == PackageManager.PERMISSION_GRANTED

    }

    private fun requestLocationPermissions() {

        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            Utils.PERMISSION_REQUEST_CODE
        )

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == Utils.PERMISSION_REQUEST_CODE){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation()
            } else {

            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentLocation(){
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            ) {
            val location: Location? =
                locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            if (location != null) {
                val latitude = location.latitude
                val longitude = location.longitude

                val mypref = SharedPref.getInstance(this@MainActivity)
                mypref.setValue("lon", longitude.toString())
                mypref.setValue("lat", latitude.toString())

            } else {



            }

        } else {



        }


    }
}