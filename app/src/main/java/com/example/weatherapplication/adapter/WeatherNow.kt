package com.example.weatherapplication.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.weatherapplication.R
import com.example.weatherapplication.WeatherList
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.time.*
import java.util.*

class WeatherToday : RecyclerView.Adapter<TodayHolder>() {

    private var weatherListOfTodayWeather = listOf<WeatherList>()





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.todayhourlylist, parent, false)
        return TodayHolder(view)


    }

    override fun getItemCount(): Int {

        return weatherListOfTodayWeather.size


    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TodayHolder, position: Int) {


        val todayForeCast = weatherListOfTodayWeather[position]




        holder.timeDisplay.text = todayForeCast.dtTxt!!.substring(11, 16).toString()





        val temperatureFahrenheit = todayForeCast.main?.temp
        val temperatureCelsius = (temperatureFahrenheit?.minus(273.15))
        val temperatureFormatted = String.format("%.2f", temperatureCelsius)


        holder.tempDisplay.text = "$temperatureFormatted °C"



        val calendar = Calendar.getInstance()

// Define the desired format
        val dateFormat = SimpleDateFormat("HH::mm")
        val formattedTime = dateFormat.format(calendar.time)


        val timeofapi = todayForeCast.dtTxt!!.split(" ")
        val partafterspace = timeofapi[1]

        Log.e("time" , " formatted time:${formattedTime}, timeofapi: ${partafterspace}")


        for ( i in todayForeCast.weather){



            if (i.icon == "01d") {


                holder.imageDisplay.setImageResource(R.drawable.sun)
            }

            if (i.icon == "01n") {
                holder.imageDisplay.setImageResource(R.drawable.moon)


            }

            if (i.icon == "02d") {

                holder.imageDisplay.setImageResource(R.drawable.cloudsandsun)


            }


            if (i.icon == "02n") {

                holder.imageDisplay.setImageResource(R.drawable.night)


            }


            if (i.icon == "03d" || i.icon == "03n") {

                holder.imageDisplay.setImageResource(R.drawable.threecloud)


            }



            if (i.icon == "10d") {

                holder.imageDisplay.setImageResource(R.drawable.rainsun)


            }


            if (i.icon == "10n") {

                holder.imageDisplay.setImageResource(R.drawable.raincloud)

            }


            if (i.icon == "04d" || i.icon == "04n") {

                holder.imageDisplay.setImageResource(R.drawable.clouds)


            }


            if (i.icon == "09d" || i.icon == "09n") {

                holder.imageDisplay.setImageResource(R.drawable.stormcloud)


            }


            if (i.icon == "11d" || i.icon == "11n") {


                holder.imageDisplay.setImageResource(R.drawable.thunderstorm)


            }


            if (i.icon == "13d" || i.icon == "13n") {

                holder.imageDisplay.setImageResource(R.drawable.snowfall)


            }

            if (i.icon == "50d" || i.icon == "50n") {


                holder.imageDisplay.setImageResource(R.drawable.wave)


            }







        }







    }



    fun setList(weatherListOfToday: List<WeatherList>) {
        this.weatherListOfTodayWeather = weatherListOfToday
    }







}




class TodayHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    val imageDisplay : ImageView = itemView.findViewById(R.id.imageDisplay)
    val tempDisplay : TextView = itemView.findViewById(R.id.tempDisplay)
    val timeDisplay : TextView = itemView.findViewById(R.id.timeDisplay)



}