package com.example.bar_code_scanner

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class QRCodeDataAdapter(
    private val qrDataList: MutableList<ScannedData>,private val itemDao: QRDataDao
) : RecyclerView.Adapter<QRCodeDataAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val qrTextView: TextView = itemView.findViewById(R.id.hisscan)
        val deleteButton: ImageButton = itemView.findViewById(R.id.del)

        fun bind(qrData: ScannedData) {
            qrTextView.text = qrData.qrData
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.his_card, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val qrData = qrDataList[position]
        holder.bind(qrData)
        holder.itemView.setOnClickListener {
            val gson = Gson()
            val qrDataJson = gson.toJson(qrData.qrData)
            val intent = Intent(holder.itemView.context, Share::class.java)
            intent.putExtra("qrData", qrDataJson)
            holder.itemView.context.startActivity(intent)
            (holder.itemView.context as Activity).finish()
        }
        holder.deleteButton.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                deleteItem(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return qrDataList.size
    }

    suspend fun deleteItem(position: Int) {
        val deletedItem = qrDataList.removeAt(position)
        notifyDataSetChanged()

        // Delete item from the Room database
        withContext(Dispatchers.IO) {
            itemDao.deleteItem(deletedItem)
        }
    }

}


