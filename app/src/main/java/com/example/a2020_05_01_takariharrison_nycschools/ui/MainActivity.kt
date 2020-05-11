package com.example.a2020_05_01_takariharrison_nycschools.ui

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.a2020_05_01_takariharrison_nycschools.App
import com.example.a2020_05_01_takariharrison_nycschools.R
import com.example.a2020_05_01_takariharrison_nycschools.deviceType
import com.example.a2020_05_01_takariharrison_nycschools.ui.device.DeviceType
import com.example.a2020_05_01_takariharrison_nycschools.ui.device.OnLifecycleEvent


class MainActivity : AppCompatActivity() {

    companion object {
        const val SCHOOL_LIST_FRAG = "school list fragment"
        const val SCHOOL_SAT_FRAG = "school sat fragment"
        const val ERROR_FRAG = "error fragment"
    }

    private lateinit var lifeCycleEvent: OnLifecycleEvent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.navigationBarColor = Color.parseColor("#555454")

        val layout = findViewById<ConstraintLayout>(R.id.mainActivity)

        val activityComponent = App.applicationComponent
            .activityComponentFactory
            .create(savedInstanceState, supportFragmentManager, layout, this)

        lifeCycleEvent = if (deviceType(resources) == DeviceType.Phone)
            activityComponent.phoneLifecycleEvent
        else activityComponent.tabletLifecycleEvent

        lifeCycleEvent.onCreate()
    }

    override fun onStart() {
        super.onStart()
        lifeCycleEvent.onStart()
    }

    override fun onStop() {
        super.onStop()
        lifeCycleEvent.onStop()
    }
}
