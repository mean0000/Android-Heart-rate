package com.example.test_00

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_result)

        /*높음,건강, 낮음 이미지*/
        val status_Heart = findViewById<ImageView>(R.id.status_heart)
        val status_Stress = findViewById<ImageView>(R.id.status_stress)
        val status_Breath = findViewById<ImageView>(R.id.status_breath)

        /*텍스트*/
        val valueText_heart = findViewById<TextView>(R.id.value_heart)
        val valueText_stress = findViewById<TextView>(R.id.value_stress)
        val valueText_breath = findViewById<TextView>(R.id.value_breath)

        /*버튼*/
        val detailBtn_Heart = findViewById<ImageButton>(R.id.Btn_heart_result)
        val detailBtn_Stress = findViewById<ImageButton>(R.id.Btn_stress_result)
        val detailBtn_Breath = findViewById<ImageButton>(R.id.Btn_breath_result)

        /*가져온 값*/
        val heartValue = intent.getDoubleExtra("Heart",0.0)
        val stressValue = intent.getDoubleExtra("Stress",0.0)
        val breathValue = intent.getIntExtra("Breath", 0)

        var heartValueInt = heartValue.toInt()

        valueText_heart.text = heartValueInt.toString()
        valueText_stress.text = stressValue.toString()
        valueText_breath.text = breathValue.toString()

        var statusHeartValue = ""
        var statusStressValue = ""
        var statusBreathValue = ""

        var statusHeart_intent = heartValue
        var statusStress_intent = stressValue
        var statusBreath_intent= breathValue


        //심박수
        //정상
        if (heartValue >= 60 && heartValue < 100){
            status_Heart.setImageResource(R.drawable.content_view_normal)
            statusHeartValue = "normal"
        //주의
        } else if((heartValue >= 50 && heartValue < 60) || (heartValue >= 100 && heartValue < 110 )){
            status_Heart.setImageResource(R.drawable.content_view_low)
            statusHeartValue = "caution"
        //경고
        } else if(heartValue < 50 || heartValue >= 110){
            status_Heart.setImageResource(R.drawable.content_view_high)
            statusHeartValue = "warning"
        }

        //스트레스
        //정상
        if(stressValue >= 0 && stressValue < 0.33){
            status_Stress.setImageResource(R.drawable.content_view_normal)
            statusStressValue = "normal"
        //주의
        }else if((stressValue >= 0.33 && stressValue < 0.5) || (stressValue >= 0.5 && stressValue < 0.66)){
            status_Stress.setImageResource(R.drawable.content_view_low)
            statusStressValue = "caution"
        //경고
        }else if(stressValue >= 0.66 && stressValue < 1) {
            status_Stress.setImageResource(R.drawable.content_view_high)
            statusStressValue = "warning"
        }

        //호흡수
        //정상
        if(breathValue>= 6 && breathValue < 19){
            status_Breath.setImageResource(R.drawable.content_view_normal)
            statusBreathValue = "normal"
        //주의
        }else if((breathValue >= 0 && breathValue < 6) || (breathValue >= 19 && breathValue < 21) ){
            status_Breath.setImageResource(R.drawable.content_view_low)
            statusBreathValue = "caution"
        //경고
        }else if(breathValue >= 21){
            status_Breath.setImageResource(R.drawable.content_view_high)
            statusBreathValue = "warning"
        }




        detailBtn_Heart.setOnClickListener{
            val intentHeart = Intent(this, HeartDetailActivity::class.java)
            intentHeart.putExtra("HeartDetail", statusHeartValue)

            intentHeart.putExtra("HeartValue", statusHeart_intent);
            intentHeart.putExtra("StressValue", statusStress_intent);
            intentHeart.putExtra("BreathValue", statusBreath_intent);

            startActivity(intentHeart)
        }

        detailBtn_Stress.setOnClickListener{
            val intentStress = Intent(this, StressDetailActivity::class.java)
            intentStress.putExtra("StressDetail", statusStressValue)

            intentStress.putExtra("HeartValue", statusHeart_intent);
            intentStress.putExtra("StressValue", statusStress_intent);
            intentStress.putExtra("BreathValue", statusBreath_intent);

            startActivity(intentStress)
        }

        detailBtn_Breath.setOnClickListener{
            val intentBreath = Intent(this, BreathDetailActivity::class.java)
            intentBreath.putExtra("BreathDetail", statusBreathValue)

            intentBreath.putExtra("HeartValue", statusHeart_intent);
            intentBreath.putExtra("StressValue", statusStress_intent);
            intentBreath.putExtra("BreathValue", statusBreath_intent);

            startActivity(intentBreath)
        }


        //안드로이드 처음으로
        val backBtn_Result = findViewById<ImageButton>(R.id.Btn_home_result)
        backBtn_Result.setOnClickListener{
            val intent = Intent(this, SplashActivity::class.java)
            startActivity(intent)
        }

    }
}