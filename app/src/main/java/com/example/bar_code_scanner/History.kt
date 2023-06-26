package com.example.bar_code_scanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class History : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var qrDataDao: QRDataDao
    private lateinit var adapter: QRCodeDataAdapter
    private lateinit var deleteButton: ImageButton
    private val qrDataList = mutableListOf<ScannedData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        // Initialize Room Database and DAO
        val appDatabase = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "qr_data_table"
        ).build()
        qrDataDao = appDatabase.yourDao()

        // Initialize RecyclerView and its adapter
        recyclerView = findViewById(R.id.scanHistory)
        adapter = QRCodeDataAdapter(qrDataList,qrDataDao)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Fetch initial data from the database and update the list
        fetchDataFromDatabase()

        // Retrieve qrData from Intent
        val qrData = intent.getStringExtra("qrData")
        if (qrData != null) {
            saveQRDataToDatabase(qrData)
        }



    }

    private fun fetchDataFromDatabase() {
        GlobalScope.launch {
            val data = qrDataDao.getAllQRData()
            qrDataList.clear()
            qrDataList.addAll(data)
            withContext(Dispatchers.Main) {
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun saveQRDataToDatabase(qrData: String) {
        val qrDataItem = ScannedData(qrData = qrData)
        GlobalScope.launch {
            qrDataDao.insert(qrDataItem)
            qrDataList.add(qrDataItem)
            withContext(Dispatchers.Main) {
                adapter.notifyItemInserted(qrDataList.size )
            }
        }
    }
}
