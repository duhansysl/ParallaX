package com.duhansysl.parallaxnew

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.duhansysl.parallaxnew.databinding.ActivityMainBinding
import com.duhansysl.parallaxnew.SecondActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var textThreshold: Float = 0f
    private var textSpeedMultiplier: Float = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.startButton.setOnClickListener {
            val text = binding.textPlain.text.toString()
            val textLength = text.length

            if (text.isEmpty()) {
                AlertDialog.Builder(this)
                    .setTitle("Hata!")
                    .setMessage("Lütfen bir metin giriniz.")
                    .setPositiveButton("Tamam") { dialog, _ -> dialog.dismiss() }
                    .show()
            } else if (textLength in 1..3) {
                textThreshold = 1f
                textSpeedMultiplier = 90f
            } else if (textLength == 4) {
                textThreshold = 2.5f
                textSpeedMultiplier = 120f
            } else if (textLength == 5) {
                textThreshold = 2.5f
                textSpeedMultiplier = 160f
            } else {
                AlertDialog.Builder(this)
                    .setTitle("Hata!")
                    .setMessage("Lütfen maksimum 5 harfli bir kelime giriniz.")
                    .setPositiveButton("Tamam") { dialog, _ -> dialog.dismiss() }
                    .show()
                return@setOnClickListener
            }

            val intent = Intent(this@MainActivity, SecondActivity::class.java).apply {
                putExtra("TEXT", text)
                putExtra("TEXT_THRESHOLD", textThreshold)
                putExtra("TEXT_SPEED_MULTIPLIER", textSpeedMultiplier)
            }
            startActivity(intent)
        }
    }
}
