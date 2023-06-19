package com.example.mye_pargne

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthentificationService {

    companion object {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.2.110:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val userAccountRoutes = retrofit.create(UserAccountRoutes::class.java)
        val compteRoutes = retrofit.create(CompteRoutes::class.java)
        val operationRoutes = retrofit.create(OperationRoutes::class.java)



    }
}