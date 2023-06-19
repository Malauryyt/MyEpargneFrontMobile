package com.example.mye_pargne

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import com.fasterxml.jackson.databind.ObjectMapper
import java.util.*

class JWTToken (private val pref: SharedPreferences){

    var token: String = pref.getString("token", "") ?: ""

    fun setTokenValue(newToken: String) {
        token = "Bearer $newToken"
        with (pref.edit()) {
            putString("token", token)
            apply()
        }
    }

    fun isValid() : Boolean {
        return token != "" && token != "Bearer "
    }

    companion object {
        var instance: JWTToken? = null

        fun getInstance(context: Context): JWTToken {
            if (instance == null) {
                instance = JWTToken(context.getSharedPreferences("prefs", Context.MODE_PRIVATE))
            }
            return instance as JWTToken
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun decodeToken(token: String): Map<String, Any> {
            val base64Url = token.split(".")[1]
            val base64 = base64Url.replace('-', '+').replace('_', '/')
            val jsonPayload = String(Base64.getDecoder().decode(base64))
            return jsonPayload.let { payload ->
                @Suppress("UNCHECKED_CAST")
                ObjectMapper().readValue(payload, Map::class.java) as Map<String, Any>
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun getIdUser(token: String): String? {
            val decode = decodeToken(token).toString()
            val regex = Regex("id_utilisateur=([\\w-]+)")

            val matchResult = regex.find(decode)

            return matchResult?.groupValues?.getOrNull(1)
        }

    }



}