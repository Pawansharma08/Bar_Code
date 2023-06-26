package com.example.bar_code_scanner

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class Share : AppCompatActivity() {

    private lateinit var texts: TextView
    private lateinit var copy: ImageView
    private lateinit var share: ImageView
    private lateinit var back: ImageView

    @SuppressLint("ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)

        texts = findViewById(R.id.scantext)
        copy = findViewById(R.id.copy)
        share = findViewById(R.id.share)
        back = findViewById(R.id.back)

        val qrData = intent.getStringExtra("qrData")
        texts.text = qrData

        copy.setOnClickListener {
            val textToCopy = texts.text.toString()
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Copied Text", textToCopy)
            clipboard.setPrimaryClip(clip)

            Toast.makeText(this, "Text copied", Toast.LENGTH_SHORT).show()
        }
        share.setOnClickListener {
            val textToShare = texts.text.toString()

            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, textToShare)

            startActivity(Intent.createChooser(shareIntent, "Share via"))
        }

        back.setOnClickListener {
            val intent = Intent(this@Share,Scan_Choose::class.java)
            startActivity(intent)
        }
    }
}