package com.example.mye_pargne

import retrofit2.Call
import retrofit2.http.*

interface UserAccountRoutes {

    @FormUrlEncoded
    @POST("/auth/authentification")
    fun logIn(@Field("login") login: String, @Field("mdp") password: String): Call<AuthentificationResult>

}