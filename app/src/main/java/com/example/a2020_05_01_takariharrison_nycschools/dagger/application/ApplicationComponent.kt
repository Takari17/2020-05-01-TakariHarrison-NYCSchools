package com.example.a2020_05_01_takariharrison_nycschools.dagger.application

import android.content.Context
import com.example.a2020_05_01_takariharrison_nycschools.dagger.activity.ActivityComponent
import com.example.a2020_05_01_takariharrison_nycschools.data.NycSchoolRepository
import com.example.a2020_05_01_takariharrison_nycschools.ui.NycSchoolViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    val nycSchoolViewModel: NycSchoolViewModel
    val nycSchoolRepository: NycSchoolRepository
    val activityComponentFactory: ActivityComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}
