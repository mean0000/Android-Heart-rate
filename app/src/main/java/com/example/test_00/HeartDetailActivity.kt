package com.example.test_00

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HeartDetailActivity :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_heart)

        val status = findViewById<ImageView>(R.id.status_heart)
        val status_ex = findViewById<ImageView>(R.id.ex_heart)
        val value = findViewById<TextView>(R.id.value_heart)


        /*가져온 값*/
        val heartDetail = intent.getStringExtra("HeartDetail")

        val heartValue = intent.getDoubleExtra("HeartValue" ,0.0)
        val stressValue = intent.getDoubleExtra("StressValue" ,0.0)
        val breathValue = intent.getIntExtra("BreathValue" ,0)

        val heartValueInt = heartValue.toInt()

        value.text = heartValueInt.toString()

        if(heartDetail == "normal"){
            status.setImageResource(R.drawable.content_view_normal)
            status_ex.setImageResource(R.drawable.ex_heart_normal)
        }else if(heartDetail == "caution"){
            status.setImageResource(R.drawable.content_view_low)
            status_ex.setImageResource(R.drawable.ex_heart_low)
        }else if(heartDetail == "warning"){
            status.setImageResource(R.drawable.content_view_high)
            status_ex.setImageResource(R.drawable.ex_heart_high)
        }




        //안드로이드 결과화면으로
        val backBtn_Heart = findViewById<ImageButton>(R.id.Btn_heart_result)
        backBtn_Heart.setOnClickListener{
            val intent = Intent(this, ResultActivity::class.java)

            intent.putExtra("Stress", stressValue)
            intent.putExtra("Heart", heartValue)
            intent.putExtra("Breath", breathValue)

            startActivity(intent)
        }

        //안드로이드 처음으로
        val backBtn_Result = findViewById<ImageButton>(R.id.Btn_home_result)
        backBtn_Result.setOnClickListener{
            val intent = Intent(this, SplashActivity::class.java)
            startActivity(intent)
        }
    }
}