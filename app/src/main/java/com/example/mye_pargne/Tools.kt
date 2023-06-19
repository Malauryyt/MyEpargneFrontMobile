package com.example.mye_pargne

import android.content.Context
import androidx.appcompat.app.AlertDialog

class Tools {
    companion object{

        fun displayError (context : Context, msg : String){

            val alertDialog = AlertDialog.Builder(context);
            alertDialog.setTitle("Error")
            alertDialog.setMessage(msg)
            val alert = alertDialog.create()
            alert.show()
        }

    }
}