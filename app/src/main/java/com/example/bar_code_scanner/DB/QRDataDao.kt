package com.example.bar_code_scanner.DB

import androidx.room.*
import com.example.bar_code_scanner.Model.ScannedData

@Dao
interface QRDataDao {

    @Delete
    suspend fun deleteItem(qrData: ScannedData)

    @Insert
    suspend fun insert(qrData: ScannedData)

    @Update
    suspend fun update(qrData: ScannedData)

    @Query("SELECT * FROM qr_data_table")
    suspend fun getAllQRData(): List<ScannedData>
}

