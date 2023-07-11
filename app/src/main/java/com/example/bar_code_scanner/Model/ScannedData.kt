package com.example.bar_code_scanner.Model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "qr_data_table")
data class ScannedData(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val qrData: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(qrData)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ScannedData> {
        override fun createFromParcel(parcel: Parcel): ScannedData {
            return ScannedData(parcel)
        }

        override fun newArray(size: Int): Array<ScannedData?> {
            return arrayOfNulls(size)
        }
    }
}


