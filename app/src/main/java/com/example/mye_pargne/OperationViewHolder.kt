package com.example.mye_pargne

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OperationViewHolder (row : View) : RecyclerView.ViewHolder(row){

        var textMoney : TextView = (row.findViewById<TextView>(R.id.textViewMoney))
        var textTiers : TextView = (row.findViewById<TextView>(R.id.textViewTiers))

}