package com.example.firstkotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {
    private var city_name: EditText? = null
    private var weather_button: Button? = null
    private var result_info: TextView? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        city_name = findViewById(R.id.city_name)
        weather_button = findViewById(R.id.weather_button)
        result_info = findViewById(R.id.result_info)

        weather_button?.setOnClickListener{
            if (city_name?.text?.toString()?.trim()?.equals("")!!)
                Toast.makeText(this, "Enter correct city name", Toast.LENGTH_LONG).show()
            else {
                val city: String = city_name?.text.toString()
                val my_key: String = "28ad9ccba080fba37209206a095f95b3"
                val url: String = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$my_key&units=metric&lang=ru"

                doAsync {
                    val apiResponse = URL(url).readText()

                    Log.d("INFO", apiResponse)

                    val weather = JSONObject(apiResponse).getJSONArray("weather")
                    val main = JSONObject(apiResponse).getJSONObject("main")
                    val wind = JSONObject(apiResponse).getJSONObject("wind")
                    val temp = main.getString("temp")
                    val realfeel = main.getString("feels_like")
                    val desc = weather.getJSONObject(0).getString("description")
                    val main_desc = weather.getJSONObject(0).getString("main")
                    val speed = wind.getString("speed")
                    val deg = wind.getInt("deg")
                    var dir = ""
                    when (deg) {
                        in 348..360 -> dir = "N"
                        in 0..11 -> dir = "N"
                        in 11..34 -> dir = "NNE"
                        in 34..56 -> dir = "NE"
                        in 56..79 -> dir = "ENE"
                        in 79..101 -> dir = "E"
                        in 101..124 -> dir = "ESE"
                        in 124..146 -> dir = "SE"
                        in 146..169 -> dir = "SSE"
                        in 169..191 -> dir = "S"
                        in 191..214 -> dir = "SSW"
                        in 214..236 -> dir = "SW"
                        in 236..259 -> dir = "WSW"
                        in 259..281 -> dir = "W"
                        in 281..304 -> dir = "WNW"
                        in 304..326 -> dir = "NW"
                        in 326..348 -> dir = "NNW"
                    }
                    result_info?.text = "Temperature: $temp\nReal feel: $realfeel\nDescription: $desc\n\nWind\nSpeed: $speed \nDirection: $dir"
                }
            }
        }
    }
}