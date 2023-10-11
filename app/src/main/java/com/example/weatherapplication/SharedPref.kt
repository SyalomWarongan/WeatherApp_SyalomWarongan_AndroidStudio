package com.example.weatherapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import java.security.AccessControlContext
import java.security.Key

class SharedPref internal constructor(private val context: Context){

    companion object {
        private const val SHARED_PREF_NAME = "myPref"
        private const val KEY_CITY = "city"

        @SuppressLint("StaticFieldLeak")
        private var instance: SharedPref? = null

        fun getInstance(context: Context): SharedPref {
            if (instance === null) {
                instance = SharedPref(context.applicationContext)
            }
            return instance!!
        }
    }

        private val pref : SharedPreferences by lazy {
            context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        }

        fun setValue(key: String, value: String) {
            pref.edit().putString(key, value).apply()
        }

        fun getValue(key: String): String? {
            return pref.getString(key, null)
        }

        fun setValueOrNull(key: String?, value: String?) {
            if (key != null && value != null) {
                pref.edit().putString(key, value).apply()
            }
        }

        fun getValueOrNull(key: String?): String? {
            if (key != null) {
                return pref.getString(key, null)
            }
            return null
        }

        fun clearCityValue() {
            pref.edit().remove(KEY_CITY).apply()
        }
    }