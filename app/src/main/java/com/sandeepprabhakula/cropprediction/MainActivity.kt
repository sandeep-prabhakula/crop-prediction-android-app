package com.sandeepprabhakula.cropprediction

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.sandeepprabhakula.cropprediction.databinding.ActivityMainBinding
import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val map = mutableListOf(
        "apple",
        "banana",
        "blackgram",
        "chickpea",
        "coconut",
        "coffee",
        "cotton",
        "grapes",
        "jute",
        "kidneybeans",
        "lentil",
        "maize",
        "mango",
        "mothbeans",
        "mungbean",
        "muskmelon",
        "orange",
        "papaya",
        "pigeonpeas",
        "pomegranate",
        "rice",
        "watermelon"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.predict.setOnClickListener {
            if (binding.nitrogen.text.isNotEmpty() &&
                binding.phosphorus.text.isNotEmpty() &&
                binding.potassium.text.isNotEmpty() &&
                binding.rainfall.text.isNotEmpty() &&
                binding.ph.text.isNotEmpty() &&
                binding.humidity.text.isNotEmpty() &&
                binding.temperature.text.isNotEmpty()
            ) {
                makeRequest()
            }
        }
    }

    private fun makeRequest() {
        val stringRequest: StringRequest = object : StringRequest(Request.Method.POST, BuildConfig.API_URL, {
            try {
                val jsonObject = JSONObject(it)
                val data = jsonObject.getString("crop")
                binding.result.text = map[data.toInt()]
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, {
            Log.e("Error", "${it.message}")
        }) {
            override fun getParams(): MutableMap<String, String> {
                return mutableMapOf(
                    "N" to binding.nitrogen.text.toString(),
                    "P" to binding.phosphorus.text.toString(),
                    "K" to binding.potassium.text.toString(),
                    "temperature" to binding.temperature.text.toString(),
                    "humidity" to binding.humidity.text.toString(),
                    "ph" to binding.ph.text.toString(),
                    "rainfall" to binding.rainfall.text.toString()
                )
            }
        }

        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }
}