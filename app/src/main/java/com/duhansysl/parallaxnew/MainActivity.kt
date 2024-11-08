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

            if (text.isEmpty()) {
                AlertDialog.Builder(this)
                    .setTitle("Hata!")
                    .setMessage("Lütfen bir metin giriniz.")
                    .setPositiveButton("Tamam") { dialog, _ -> dialog.dismiss() }
                    .show()
            } else {
                val intent = Intent(this@MainActivity, SecondActivity::class.java).apply {
                    putExtra("TEXT", text)
                }
                startActivity(intent)
            }
        }
    }
}