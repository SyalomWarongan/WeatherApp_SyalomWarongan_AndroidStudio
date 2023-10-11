package com.example.weatherapp.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.weatherapplication.R

import com.example.weatherapplication.WeatherList
import java.text.SimpleDateFormat
import java.util.*

class ForeCastAdapter : RecyclerView.Adapter<ForeCastHolder>() {


    private var listofforecast = listOf<WeatherList>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForeCastHolder {


        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.upcomingforecast, parent, false)
        return ForeCastHolder(view)




    }

    override fun getItemCount(): Int {

        return listofforecast.size




    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ForeCastHolder, position: Int) {
        val forecastObject = listofforecast[position]



        for (i in forecastObject.weather){

            holder.description.text = i.description!!



        }





        holder.humiditiy.text = forecastObject.main!!.humidity.toString()
        holder.windspeed.text = forecastObject.wind?.speed.toString()


        val temperatureFahrenheit = forecastObject.main?.temp
        val temperatureCelsius = (temperatureFahrenheit?.minus(273.15))
        val temperatureFormatted = String.format("%.2f", temperatureCelsius)


        holder.temp.text = "$temperatureFormatted °C"


        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val date = inputFormat.parse(forecastObject.dtTxt!!)
        val outputFormat = SimpleDateFormat("d MMMM EEEE", Locale.getDefault())
        val dateanddayname = outputFormat.format(date!!)

        holder.dateDayName.text = dateanddayname





        for (i in forecastObject.weather) {


            if (i.icon == "01d") {


                holder.imageGraphic.setImageResource(R.drawable.sun)
                holder.smallIcon.setImageResource(R.drawable.sun)
            }

            if (i.icon == "01n") {
                holder.imageGraphic.setImageResource(R.drawable.moon)
                holder.smallIcon.setImageResource(R.drawable.moon)


            }

            if (i.icon == "02d") {

                holder.imageGraphic.setImageResource(R.drawable.cloudsandsun)
                holder.smallIcon.setImageResource(R.drawable.cloudsandsun)


            }


            if (i.icon == "02n") {

                holder.imageGraphic.setImageResource(R.drawable.night)
                holder.smallIcon.setImageResource(R.drawable.night)


            }


            if (i.icon == "03d" || i.icon == "03n") {

                holder.imageGraphic.setImageResource(R.drawable.threecloud)
                holder.smallIcon.setImageResource(R.drawable.threecloud)


            }



            if (i.icon == "10d") {

                holder.imageGraphic.setImageResource(R.drawable.rainsun)
                holder.smallIcon.setImageResource(R.drawable.rainsun)


            }


            if (i.icon == "10n") {

                holder.imageGraphic.setImageResource(R.drawable.raincloud)
                holder.smallIcon.setImageResource(R.drawable.raincloud)


            }


            if (i.icon == "04d" || i.icon == "04n") {

                holder.imageGraphic.setImageResource(R.drawable.clouds)
                holder.smallIcon.setImageResource(R.drawable.clouds)



            }


            if (i.icon == "09d" || i.icon == "09n") {

                holder.imageGraphic.setImageResource(R.drawable.stormcloud)
                holder.smallIcon.setImageResource(R.drawable.stormcloud)



            }


            if (i.icon == "11d" || i.icon == "11n") {


                holder.imageGraphic.setImageResource(R.drawable.thunderstorm)
                holder.smallIcon.setImageResource(R.drawable.thunderstorm)



            }


            if (i.icon == "13d" || i.icon == "13n") {

                holder.imageGraphic.setImageResource(R.drawable.snowfall)
                holder.smallIcon.setImageResource(R.drawable.snowfall)


            }

            if (i.icon == "50d" || i.icon == "50n") {


                holder.imageGraphic.setImageResource(R.drawable.wave)
                holder.smallIcon.setImageResource(R.drawable.wave)


            }

        }

    }





    fun setList(newlist: List<WeatherList>) {

        this.listofforecast = newlist

    }


}

class ForeCastHolder(itemView: View) : ViewHolder(itemView){

    val imageGraphic: ImageView = itemView.findViewById(R.id.imageGraphic)
    val description : TextView = itemView.findViewById(R.id.weatherDescr)
    val humiditiy : TextView = itemView.findViewById(R.id.humidity)
    val windspeed : TextView = itemView.findViewById(R.id.windSpeed)

    val temp : TextView = itemView.findViewById(R.id.tempDisplayForeCast)
    val smallIcon : ImageView = itemView.findViewById(R.id.smallIcon)


    val dateDayName : TextView = itemView.findViewById(R.id.dayDateText)





}