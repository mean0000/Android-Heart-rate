package com.example.test_00

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class WarningActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_warning)

        val gendeValue = intent.getIntExtra("Gender",0)
        val ageValue = intent.getIntExtra("Age",0)

        //다음 화면으로 이동
        val nextBtn_Warning = findViewById<ImageButton>(R.id.Btn_measure_start)
        nextBtn_Warning.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)

            intent.putExtra("Age", ageValue)
            intent.putExtra("Gender", gendeValue)

            startActivity(intent)
            finish()
        }

        //안드로이드 처음으로
        val backBtn_Warning = findViewById<ImageButton>(R.id.Btn_measure_end)
        backBtn_Warning.setOnClickListener{
            val intent = Intent(this, SplashActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}