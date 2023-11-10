package com.example.novascotiawrittendrivingtest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_main)

                val buttonToSecondActivity: Button = findViewById(R.id.buttonToSecondActivity)
                buttonToSecondActivity.setOnClickListener {

                        val intent = Intent(this, DrivingTestActivity::class.java)
                        startActivity(intent)
                }
        }
}
