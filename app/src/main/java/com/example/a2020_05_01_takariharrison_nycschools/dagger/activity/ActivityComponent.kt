package com.example.a2020_05_01_takariharrison_nycschools.dagger.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import com.example.a2020_05_01_takariharrison_nycschools.dagger.application.ApplicationModule
import com.example.a2020_05_01_takariharrison_nycschools.ui.device.phone.PhoneLifecycleEvent
import com.example.a2020_05_01_takariharrison_nycschools.ui.device.tablet.TabletLifecycleEvent
import dagger.BindsInstance
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ApplicationModule::class])
interface ActivityComponent {

    val phoneLifecycleEvent: PhoneLifecycleEvent
    val tabletLifecycleEvent: TabletLifecycleEvent

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance savedInstanceState: Bundle?,
            @BindsInstance supportFragmentManager: FragmentManager,
            @BindsInstance currentLayout: ConstraintLayout,
            @BindsInstance activity: AppCompatActivity
        ): ActivityComponent
    }
}
