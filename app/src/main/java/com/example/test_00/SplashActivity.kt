package com.example.test_00

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import java.time.Duration

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //다음 화면으로 이동
        val imageBtn = findViewById<ImageButton>(R.id.Btn_start)
        imageBtn.setOnClickListener{
            val intent = Intent(this, GenderActivity::class.java)
            startActivity(intent)
        }

        //안드로이드 종료
        val imageBtn_end = findViewById<ImageButton>(R.id.Btn_end)
        imageBtn_end.setOnClickListener{
            finishAffinity()
        }

    }
}