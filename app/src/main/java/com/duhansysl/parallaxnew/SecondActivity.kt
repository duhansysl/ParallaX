package com.duhansysl.parallaxnew

import com.duhansysl.parallaxnew.databinding.ActivitySecondBinding
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlin.math.abs

class SecondActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var binding: ActivitySecondBinding
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var lastX = 0f
    private var textToDisplay: String = "TEXT"  // Intent ile alınacak metin
    private var translationX = 0f  // TextView'in x eksenindeki konumu
    private val threshold = 1f  // Eşik değeri
    private val moveSpeedMultiplier = 90f  // Hareket hızını ayarlamak için çarpan
    private var isMoving = false  // Hareket durumunu takip etmek için
    private var handler = Handler()  // Handler oluşturma

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // View binding'i başlatma
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Intent ile gelen metni al
        textToDisplay = intent.getStringExtra("TEXT") ?: "Default Text"

        // TextView'e metni ata
        binding.textView.text = textToDisplay

        // Sensörleri başlat
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)

        // Kayan metni başlat
        startScrollingText()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val x = it.values[0]

            // Eşik değerine göre hareket algıla
            if (abs(x - lastX) > threshold) {
                translationX += (x - lastX) * moveSpeedMultiplier
                binding.textView.translationX = translationX  // TextView'in konumunu güncelle
                lastX = x
                isMoving = true  // Hareket algılandığında durumu güncelle
            } else {
                isMoving = false  // Hareket algılanmadığında durumu güncelle
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    private fun startScrollingText() {
        handler.post(object : Runnable {
            override fun run() {
                // Metin hareket ediyor mu kontrol et
                if (isMoving) {
                    // Burada herhangi bir ek güncelleme yapılmasına gerek yok
                    // TextView zaten sensör verileri ile güncelleniyor
                }
                // Kayan metin efektini sürekli hale getirmek için tekrar çalıştır
                handler.postDelayed(this, 50) // Her 50 ms'de güncelle
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(this)
        handler.removeCallbacksAndMessages(null)  // Tüm handler mesajlarını kaldır
    }
}
