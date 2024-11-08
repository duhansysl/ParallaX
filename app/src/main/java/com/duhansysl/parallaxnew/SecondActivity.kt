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
    private var textToDisplay: String = "TEXT"
    private var translationX = 0f
    private var isMoving = false
    private var handler = Handler()
    private var sensorThreshold: Float = 0f
    private var textSpeedMultiplier: Float = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        textToDisplay = intent.getStringExtra("TEXT") ?: ""
        sensorThreshold = intent.getFloatExtra("TEXT_THRESHOLD", 1f)
        textSpeedMultiplier = intent.getFloatExtra("TEXT_SPEED_MULTIPLIER", 1f)

        binding.textView.text = textToDisplay

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)

        startScrollingText()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val x = it.values[0]

            if (abs(x - lastX) > sensorThreshold) {
                translationX += (x - lastX) * textSpeedMultiplier
                binding.textView.translationX = translationX
                lastX = x
                isMoving = true
            } else {
                isMoving = false
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    private fun startScrollingText() {
        handler.post(object : Runnable {
            override fun run() {
                if (isMoving) {
                }
                handler.postDelayed(this, 50)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(this)
        handler.removeCallbacksAndMessages(null)
    }
}
