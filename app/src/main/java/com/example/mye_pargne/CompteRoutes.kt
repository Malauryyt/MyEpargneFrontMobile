package com.example.mye_pargne

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.Header
import retrofit2.http.POST

interface CompteRoutes {

    @POST("/compte/soldecompte")
    fun getSolde(@Header("Authorization") token: String, @Body compteSErvice : CompteService ): Call<compteSolde>

    @POST("/compte/ajoutcompte")
    fun addArgent(@Header("Authorization") token: String, @Body compteAjout : CompteAJout ): Call<String>

    @POST("/compte/retraitcompte")
    fun removeArgent(@Header("Authorization") token: String, @Body compteRetrait : CompteRetrait ): Call<String>

}

data class CompteAJout ( var id : Int, var montant : Double, var tiers: String, var moyenpaiment : String, var description : String ): java.io.Serializable {}
data class CompteRetrait ( var id : Int, var montant : Double, var lieulien: String, var moyenpaiment : String, var description : String, var id_categorie : Int ): java.io.Serializable {}

