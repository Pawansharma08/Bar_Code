package com.example.bar_code_scanner

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import java.io.IOException


//class Scan : AppCompatActivity() {
//    private lateinit var cameraSource: CameraSource
//    private lateinit var barcodeDetector: BarcodeDetector
//    private lateinit var cameraPreview: SurfaceView
//    private lateinit var scanButton: Button
//    private lateinit var qrCodeImage: ImageView
//
//
//
//    companion object {
//        private const val CAMERA_PERMISSION_REQUEST_CODE = 100
//    }
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_scan)
//
//        cameraPreview = findViewById(R.id.cameraPreview)
//        scanButton = findViewById(R.id.scanButton)
//        qrCodeImage = findViewById(R.id.qrCodeImage)
//
//        setupBarcodeScanner()
//        setupCameraPreview()
//        setupScanButton()
//    }
//
//    private fun setupBarcodeScanner() {
//        barcodeDetector = BarcodeDetector.Builder(this)
//            .setBarcodeFormats(Barcode.QR_CODE) // Set only QR_CODE format
//            .build()
//
//        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
//            override fun release() {
//            }
//
//            override fun receiveDetections(detections: Detector.Detections<Barcode>) {
//                val barcodes: SparseArray<Barcode> = detections.detectedItems
//                if (barcodes.size() != 0) {
//                    val scannedData: String = barcodes.valueAt(0).displayValue
//                    generateQRCode(scannedData)
//                }
//            }
//        })
//
//        if (!barcodeDetector.isOperational) {
//            Toast.makeText(this, "Could not set up the barcode detector", Toast.LENGTH_SHORT).show()
//        }
//
//        cameraSource = CameraSource.Builder(this, barcodeDetector)
//            .setAutoFocusEnabled(true)
//            .build()
//    }
//
//    private fun setupCameraPreview() {
//        cameraPreview.holder.addCallback(object : SurfaceHolder.Callback {
//            override fun surfaceCreated(holder: SurfaceHolder) {
//                try {
//                    if (ContextCompat.checkSelfPermission(this@Scan, Manifest.permission.CAMERA)
//                        == PackageManager.PERMISSION_GRANTED
//                    ) {
//                        cameraSource.start(cameraPreview.holder)
//                    } else {
//                        ActivityCompat.requestPermissions(
//                            this@Scan,
//                            arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
//
//                    }
//                } catch (e: IOException) {
//                    Log.e(TAG, "Error starting camera preview: ${e.message}")
//                }
//            }
//
//            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
//            }
//
//            override fun surfaceDestroyed(holder: SurfaceHolder) {
//                cameraSource.stop()
//            }
//        })
//    }
//
//    private fun setupScanButton() {
//        scanButton.setOnClickListener {
//            qrCodeImage.visibility = View.GONE
//            if (ContextCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.CAMERA
//                ) == PackageManager.PERMISSION_GRANTED
//            ) {
//                cameraSource.takePicture(null, null)
//            } else {
//                ActivityCompat.requestPermissions(
//                    this,
//                    arrayOf(Manifest.permission.CAMERA),
//                    CAMERA_PERMISSION_REQUEST_CODE
//                )
//            }
//        }
//    }
//
////    private fun generateQRCode(data: String) {
////        try {
////            val writer = QRCodeWriter()
////            val bitMatrix: BitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 200, 200)
////            val width: Int = bitMatrix.width
////            val height: Int = bitMatrix.height
////            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
////            for (x in 0 until width) {
////                for (y in 0 until height) {
////                    bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
////                }
////            }
////
////            runOnUiThread {
////                qrCodeImage.setImageBitmap(bitmap)
////                qrCodeImage.visibility = View.VISIBLE
////
////                val intent = Intent(this@Scan,Share::class.java)
////                intent.putExtra("qrData", data)
////                startActivity(intent)
////                finish()
////            }
////        } catch (e: WriterException) {
////            Log.e(TAG, "Error generating QR code: ${e.message}")
////        }
////    }
//
//    private fun generateQRCode(data: String) {
//
//        runOnUiThread {
//            qrCodeImage.visibility = View.VISIBLE
//
//            val intent = Intent(this@Scan,Share::class.java)
//            intent.putExtra("qrData", data)
//            startActivity(intent)
//            finish()
//        }
//    }
//}

class Scan : AppCompatActivity() {
    private lateinit var cameraSource: CameraSource
    private lateinit var barcodeDetector: BarcodeDetector
    private lateinit var cameraPreview: SurfaceView
    private lateinit var scanButton: Button
    private lateinit var qrCodeImage: ImageView

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        cameraPreview = findViewById(R.id.cameraPreview)
        scanButton = findViewById(R.id.scanButton)
        qrCodeImage = findViewById(R.id.qrCodeImage)

        setupBarcodeScanner()
        setupCameraPreview()
        setupScanButton()
    }

    private fun setupBarcodeScanner() {
        barcodeDetector = BarcodeDetector.Builder(this)
            .setBarcodeFormats(Barcode.QR_CODE)
            .build()

        if (!barcodeDetector.isOperational) {
            Toast.makeText(this, "Could not set up the barcode detector", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupCameraPreview() {
        cameraSource = CameraSource.Builder(this, barcodeDetector)
            .setAutoFocusEnabled(true)
            .build()

        cameraPreview.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    if (ContextCompat.checkSelfPermission(
                            this@Scan,
                            Manifest.permission.CAMERA
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        cameraSource.start(cameraPreview.holder)
                    } else {
                        ActivityCompat.requestPermissions(
                            this@Scan,
                            arrayOf(Manifest.permission.CAMERA),
                            CAMERA_PERMISSION_REQUEST_CODE
                        )
                    }
                } catch (e: IOException) {
                    Log.e(TAG, "Error starting camera preview: ${e.message}")
                }
            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource.stop()
            }
        })

        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {}

            override fun receiveDetections(detections: Detector.Detections<Barcode>) {
                val barcodes: SparseArray<Barcode> = detections.detectedItems
                if (barcodes.size() != 0) {
                    val scannedData: String = barcodes.valueAt(0).displayValue
                    generateQRCode(scannedData)
                }
            }
        })
    }

    private fun setupScanButton() {
        scanButton.setOnClickListener {
            qrCodeImage.visibility = View.GONE
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                cameraSource.takePicture(null, null)
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    private fun releaseResources() {
        cameraSource.release()
        barcodeDetector.release()
    }

    private fun generateQRCode(data: String) {
        runOnUiThread {
            qrCodeImage.visibility = View.VISIBLE

            val intent = Intent(this@Scan, Share::class.java)
            intent.putExtra("qrData", data)
            startActivity(intent)
            finish()
        }
    }
}



