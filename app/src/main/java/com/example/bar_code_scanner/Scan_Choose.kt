package com.example.bar_code_scanner

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceView
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

class Scan_Choose : AppCompatActivity() {

    private lateinit var scanButton: ImageButton
    private lateinit var texts: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_choose)

        scanButton = findViewById(R.id.click)

        scanButton.setOnClickListener {
            val intent = Intent(this,Scan::class.java)
            startActivity(intent)
        }


    }

}