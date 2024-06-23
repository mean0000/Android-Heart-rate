package com.example.test_00

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.SurfaceTexture
import android.hardware.SensorManager
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.TextureView
import android.view.WindowManager
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.deepmedi.dm_camera.camera.ActiveCamera
import com.deepmedi.dm_data.processing_image.BioSignalData
import com.deepmedi.dm_data.struct.DataRGB
import com.deepmedi.dm_data.utils.ConfigCam
import com.deepmedi.dm_data.utils.FaceDetector
import com.deepmedi.dm_data.utils.MeasureType
import com.deepmedi.dm_data.utils.ReverseType
import com.deepmedi.dm_data.utils.RotationType
import com.example.test_00.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val measureTime = 15
    private val readyTime = 3
    private var rotation: Int = Configuration.ORIENTATION_PORTRAIT
    private lateinit var binding: ActivityMainBinding

    private val cameraManager: CameraManager by lazy {
        this.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    }
    private val sensorManager: SensorManager by lazy {
        this.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }


    private var gendeValueMain = 0;
    private var ageValueMain = 0;


    private val activeCamera: ActiveCamera by lazy {
        ConfigCam.externalCheck = this.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_EXTERNAL)
        ActiveCamera.getInstance(
            cameraManager = cameraManager,
            sensorManager = sensorManager,
            camMode = ConfigCam.MODE_FACE,
            accessKey = "zBVd4vMLImLyM8hJQ5914Kj8tuKagVx7YyzTiZ70",
            measureTime = measureTime,
            readyTime = readyTime
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //Log.v("성별","ㅇㅇㅇ"+ intentGender);

        val gendeValue = intent.getIntExtra("Gender",0)
        val ageValue = intent.getIntExtra("Age",0)

        gendeValueMain = gendeValue
        ageValueMain = ageValue


        initTextureView()
        initFlows()
    }

    override fun onResume() {
        super.onResume()
        rotation = resources.configuration.orientation
        // 화면에서 얼굴이 차지하는 비율 (ex 10%~90% 차지하고 있어야 얼굴 인식)
        activeCamera.setFaceRegionRange(
            minPercent = 0.1f,
            maxPercent = 0.9f
        )
        // X는 좌우, Y는 위아래
        // coordinate는 얼굴 좌표 기준
        // offset는 얼굴 좌표 기준 허용 가능한 +- 범위
        // ex) 얼굴 좌표 기준(coordinate) +- 45%(offset) 안에 들어와야 얼굴 인식
        activeCamera.setFaceCenterRange(
            coordinateX = 0.5f,
            coordinateY = 0.5f,
            offsetX = 0.45f,
            offsetY = 0.45f
        )
        activeCamera.startExtractor(
            faceDetectorMode = FaceDetector.detectorMode,
            rotation = rotation,
            measureType = MeasureType.MEASURE_TYPE_FACE
        )
    }

    override fun onPause() {
        super.onPause()
        activeCamera.stopExtractor(MeasureType.MEASURE_TYPE_FACE)
    }

    override fun onDestroy() {
        super.onDestroy()
        activeCamera.closeCamera()
    }

    private fun initTextureView() {
        binding.measureTextureView.let { textureView ->
            textureView.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
                override fun onSurfaceTextureAvailable(texture: SurfaceTexture, width: Int, height: Int) {
                    lifecycleScope.launch(Dispatchers.IO) {
                        // 카메라 회전 또는 반전이 필요할 때 사용 / 필요없으면 삭제해도 무관
                        activeCamera.transformTexture(
                            textureView,
                            reverseType = ReverseType.NONE,
                            rotationType = RotationType.ROTATION_0,
                        )
                        activeCamera.selectCamera(textureView, ConfigCam.NORMAL_IMAGE)
                    }
                }
                override fun onSurfaceTextureSizeChanged(texture: SurfaceTexture, width: Int, height: Int) = Unit
                override fun onSurfaceTextureDestroyed(texture: SurfaceTexture) = true
                override fun onSurfaceTextureUpdated(texture: SurfaceTexture) = Unit

            }
        }
    }

    private fun initFlows() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    activeCamera.getFaceCoordinateExtractorFaceCoordinate().collect { faceCoordinate ->
                        if (faceCoordinate != null) {
                            if (faceCoordinate.height != 0 && faceCoordinate.width != 0 && faceCoordinate.faceStruct != null){
                                activeCamera.setSubFaceCoordinate(faceCoordinate.check, faceCoordinate.faceStruct)
                                if (!faceCoordinate.check) {
                                    // 설정 해놓은 범위 내에 얼굴이 없을 때
                                    activeCamera.clearBioSignalExtractorData()
                                    Log.v("test","얼굴이 없음")
                                }

                        }
                        else{
                            // 얼굴을 놓쳤을 때
                            activeCamera.clearBioSignalExtractorData()
                                Log.v("test","얼굴이 놓침")
                        }
                        activeCamera.setPulseSignalExtractorCheck(faceCoordinate.check)
                        activeCamera.setRespSignalExtractorCheck(faceCoordinate.check)
                            Log.v("test","체크")
                    }
                }
            }
            launch {
                activeCamera.stackFaceMeasureData()
            }
            launch {
                activeCamera.getBioSignalExtractorCount().collect {
                // 측정 카운트
                    Log.v("test","카운트" + it)
                    startGauge(it)
                }
            }
            launch {
                activeCamera.getBioSignalExtractorComplete().collect {
                    // 측정이 완료 됐을 때
                        if (it) {
                            if(gendeValueMain == 0){ //남성
                                // 측정값
                                val bioSignalData = activeCamera.getBioSignalExtractorData(
                                    MeasureType.MEASURE_TYPE_FACE,

                                    age = ageValueMain,
                                    gender = gendeValueMain,
                                    weight = 66,
                                    height = 174,
                                )
                                Log.v("test","데이터" + bioSignalData)
                                activeCamera.clearBioSignalExtractorData()

                                val stressValue = bioSignalData.component3()
                                val heartValue = bioSignalData.component4()
                                val breathValue = bioSignalData.component11()

                                Log.v("test","스트레스" + stressValue)
                                Log.v("test","심박수" + heartValue)
                                Log.v("test","호흡수" + breathValue)
                                alert(stressValue, heartValue, breathValue)

                            }else if(gendeValueMain == 1){ //여성
                                // 측정값
                                val bioSignalData = activeCamera.getBioSignalExtractorData(
                                    MeasureType.MEASURE_TYPE_FACE,

                                    age = ageValueMain,
                                    gender = gendeValueMain,
                                    weight = 52,
                                    height = 160,
                                )
                                Log.v("test","데이터" + bioSignalData)
                                activeCamera.clearBioSignalExtractorData()

                                val stressValue = bioSignalData.component3()
                                val heartValue = bioSignalData.component4()
                                val breathValue = bioSignalData.component11()

                                Log.v("test","스트레스" + stressValue)
                                Log.v("test","심박수" + heartValue)
                                Log.v("test","호흡수" + breathValue)
                                alert(stressValue, heartValue, breathValue)
                            }

                        }

                    }
                }
            }
        }
    }

    fun alert(stress:Double, heart:Double, breath:Int){
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("Stress", stress)
        intent.putExtra("Heart", heart)
        intent.putExtra("Breath", breath)
        startActivity(intent);
    }

    private fun startGauge(time:Float){
        val countDownText = findViewById<TextView>(R.id.countDownText)
        var test = time
        if(test >= 0){
            var timetest = ((test /15) * 100).toInt()
            countDownText.text = timetest.toString()
        }
        //countDownText.text = time.toString();

    }
}



