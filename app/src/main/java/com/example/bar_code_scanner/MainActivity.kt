package com.example.bar_code_scanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar

class MainActivity : AppCompatActivity() {

    private val SPLASH_DELAY: Long = 2000 // 2 seconds

    private lateinit var progressBar: ProgressBar
    private val progressMax = 100
    private val progressDelay: Long = 50 // Delay in milliseconds
    private val progressIncrement = (progressMax * progressDelay / 1600).toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progress)
        progressBar.max = progressMax

        simulateProgress()

        Handler().postDelayed({
            // Start your next activity here
            val intent = Intent(this, Scan_Choose::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_DELAY)

    }
    private fun simulateProgress() {
        val handler = Handler(Looper.getMainLooper())
        var progress = 0

        handler.postDelayed(object : Runnable {
            override fun run() {
                progress += progressIncrement
                progressBar.progress = progress

                if (progress < progressMax) {
                    handler.postDelayed(this, progressDelay)
                }
            }
        }, progressDelay)
    }
}