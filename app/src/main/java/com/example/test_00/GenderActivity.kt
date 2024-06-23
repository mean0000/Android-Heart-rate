package com.example.test_00

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.test_00.databinding.ActivityMainBinding


class GenderActivity :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gender)

        val manBtn = findViewById<ImageButton>(R.id.Btn_gender_man)
        val womanBtn = findViewById<ImageButton>(R.id.Btn_gender_woman)

        val nextBtn_Gender = findViewById<ImageButton>(R.id.Btn_gender_next)
        val backBtn_Gender = findViewById<ImageButton>(R.id.Btn_gender_back)

        nextBtn_Gender.isEnabled = false
        nextBtn_Gender.setImageResource(R.drawable.btn_next_disable)

        manBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("Gender", 0)

            manBtn.setImageResource(R.drawable.content_btn_man_activate)
            womanBtn.setImageResource(R.drawable.content_btn_woman_disable)

            nextBtn_Gender.setImageResource(R.drawable.btn_next)
            nextBtn_Gender.isEnabled = true
        }

        womanBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("Gender", 1)
            manBtn.setImageResource(R.drawable.content_btn_man_disable)
            womanBtn.setImageResource(R.drawable.content_btn_woman_activate)

            nextBtn_Gender.setImageResource(R.drawable.btn_next)
            nextBtn_Gender.isEnabled = true
        }





        //다음 화면으로 이동
        nextBtn_Gender.setOnClickListener{
            val intent = Intent(this, AgeActivity::class.java)
            startActivity(intent)
            finish()
        }

        //안드로이드 종료
        backBtn_Gender.setOnClickListener{
            val intent = Intent(this, SplashActivity::class.java)
            startActivity(intent)
        }
    }
}