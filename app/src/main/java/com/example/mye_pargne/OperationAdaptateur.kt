package com.example.mye_pargne

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView

class OperationAdaptateur (var context : Context, var maliste: Operation) : RecyclerView.Adapter<OperationViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OperationViewHolder {
        return OperationViewHolder( LayoutInflater.from(context).inflate(R.layout.operation_cell, parent, false))
    }

    override fun onBindViewHolder(holder: OperationViewHolder, position: Int) {

        // je récupère à la bonne position la bonne category
        var uneOpe = maliste.operationdujour[position]

        //J'écris donc à la bonne position le nom de celle-ci


        if(uneOpe.tiers_opération == null){
            holder.textTiers.setText(uneOpe.lieulien_operation)

            holder.textMoney.setText("- " + uneOpe.montant_operation + " €")
            holder.textMoney.setTextColor(Color.parseColor("#f53636"));
        }else{
            holder.textTiers.setText(uneOpe.tiers_opération)

            holder.textMoney.setText("+ " + uneOpe.montant_operation + " €")
            holder.textMoney.setTextColor(Color.parseColor("#8BC34A"));

        }


    }

    override fun getItemCount(): Int {
        var uneOpe = maliste.operationdujour.count()
        return uneOpe
    }
}