package com.example.sensorequipo5

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnAcelerometro = findViewById<Button>(R.id.btnAcelerometro)
        val btnDetectorMetales = findViewById<Button>(R.id.btnDetectorMetales)

        btnAcelerometro.setOnClickListener {
            val intent = Intent(this, AcelerometroActivity::class.java)
            startActivity(intent)
        }

        btnDetectorMetales.setOnClickListener {
            val intent = Intent(this, DetectorMetalesActivity::class.java)
            startActivity(intent)
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}