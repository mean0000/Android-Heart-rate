package com.example.test_00

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class AgeActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_age)


        val twentyBtn = findViewById<ImageButton>(R.id.Btn_age_20)
        val thirytBtn = findViewById<ImageButton>(R.id.Btn_age_30)
        val fortyBtn = findViewById<ImageButton>(R.id.Btn_age_40)
        val fiftyBtn = findViewById<ImageButton>(R.id.Btn_age_50)
        val sixtyBtn = findViewById<ImageButton>(R.id.Btn_age_60)
        val seventyBtn = findViewById<ImageButton>(R.id.Btn_age_70)

        val nextBtn_Age = findViewById<ImageButton>(R.id.Btn_age_next)
        val imageBtn_end = findViewById<ImageButton>(R.id.Btn_age_back)


        /*가져온 값*/
        val gendeValue = intent.getIntExtra("Gender",0)

        nextBtn_Age.isEnabled = false
        nextBtn_Age.setImageResource(R.drawable.btn_next_disable)

        twentyBtn.setOnClickListener{
            twentyBtn.setImageResource(R.drawable.content_btn_20_activate)
            thirytBtn.setImageResource(R.drawable.content_btn_30_disable)
            fortyBtn.setImageResource(R.drawable.content_btn_40_disable)
            fiftyBtn.setImageResource(R.drawable.content_btn_50_disable)
            sixtyBtn.setImageResource(R.drawable.content_btn_60_disable)
            seventyBtn.setImageResource(R.drawable.content_btn_70_disable)

            intent.putExtra("Age", 20)
            intent.putExtra("Gender", gendeValue)

            nextBtn_Age.isEnabled = true
            nextBtn_Age.setImageResource(R.drawable.btn_next)
        }

        thirytBtn.setOnClickListener{
            twentyBtn.setImageResource(R.drawable.content_btn_20_disable)
            thirytBtn.setImageResource(R.drawable.content_btn_30_activate)
            fortyBtn.setImageResource(R.drawable.content_btn_40_disable)
            fiftyBtn.setImageResource(R.drawable.content_btn_50_disable)
            sixtyBtn.setImageResource(R.drawable.content_btn_60_disable)
            seventyBtn.setImageResource(R.drawable.content_btn_70_disable)

            intent.putExtra("Age", 30)
            intent.putExtra("Gender", gendeValue)


            nextBtn_Age.isEnabled = true
            nextBtn_Age.setImageResource(R.drawable.btn_next)
        }

        fortyBtn.setOnClickListener{
            twentyBtn.setImageResource(R.drawable.content_btn_20_disable)
            thirytBtn.setImageResource(R.drawable.content_btn_30_disable)
            fortyBtn.setImageResource(R.drawable.content_btn_40_activate)
            fiftyBtn.setImageResource(R.drawable.content_btn_50_disable)
            sixtyBtn.setImageResource(R.drawable.content_btn_60_disable)
            seventyBtn.setImageResource(R.drawable.content_btn_70_disable)

            intent.putExtra("Age", 40)
            intent.putExtra("Gender", gendeValue)

            nextBtn_Age.isEnabled = true
            nextBtn_Age.setImageResource(R.drawable.btn_next)
        }

        fiftyBtn.setOnClickListener{
            twentyBtn.setImageResource(R.drawable.content_btn_20_disable)
            thirytBtn.setImageResource(R.drawable.content_btn_30_disable)
            fortyBtn.setImageResource(R.drawable.content_btn_40_disable)
            fiftyBtn.setImageResource(R.drawable.content_btn_50_activate)
            sixtyBtn.setImageResource(R.drawable.content_btn_60_disable)
            seventyBtn.setImageResource(R.drawable.content_btn_70_disable)

            intent.putExtra("Age", 50)
            intent.putExtra("Gender", gendeValue)

            nextBtn_Age.isEnabled = true
            nextBtn_Age.setImageResource(R.drawable.btn_next)
        }

        sixtyBtn.setOnClickListener{
            twentyBtn.setImageResource(R.drawable.content_btn_20_disable)
            thirytBtn.setImageResource(R.drawable.content_btn_30_disable)
            fortyBtn.setImageResource(R.drawable.content_btn_40_disable)
            fiftyBtn.setImageResource(R.drawable.content_btn_50_disable)
            sixtyBtn.setImageResource(R.drawable.content_btn_60_activate)
            seventyBtn.setImageResource(R.drawable.content_btn_70_disable)

            intent.putExtra("Age", 60)
            intent.putExtra("Gender", gendeValue)

            nextBtn_Age.isEnabled = true
            nextBtn_Age.setImageResource(R.drawable.btn_next)
        }

        seventyBtn.setOnClickListener{
            twentyBtn.setImageResource(R.drawable.content_btn_20_disable)
            thirytBtn.setImageResource(R.drawable.content_btn_30_disable)
            fortyBtn.setImageResource(R.drawable.content_btn_40_disable)
            fiftyBtn.setImageResource(R.drawable.content_btn_50_disable)
            sixtyBtn.setImageResource(R.drawable.content_btn_60_disable)
            seventyBtn.setImageResource(R.drawable.content_btn_70_activate)

            intent.putExtra("Age", 70)
            intent.putExtra("Gender", gendeValue)

            nextBtn_Age.isEnabled = true
            nextBtn_Age.setImageResource(R.drawable.btn_next)
        }



        //다음 화면으로 이동
        nextBtn_Age.setOnClickListener{
            val intent = Intent(this, WarningActivity::class.java)
            startActivity(intent)
            finish()
        }

        //안드로이드 종료
        imageBtn_end.setOnClickListener{
            val intent = Intent(this, GenderActivity::class.java)
            startActivity(intent)
        }
    }
}