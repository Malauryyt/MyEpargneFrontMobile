package com.example.mye_pargne

import retrofit2.Call
import retrofit2.http.*

interface OperationRoutes {
    @GET("/operation/journaliere/{id}")
    fun getOpe(@Header("Authorization") token: String, @Path("id") id: Int ): Call<Operation>
}

data class Operation ( var operationdujour: List<Operationdujour>): java.io.Serializable {}