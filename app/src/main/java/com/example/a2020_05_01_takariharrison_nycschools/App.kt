package com.example.a2020_05_01_takariharrison_nycschools

import android.app.Application
import com.example.a2020_05_01_takariharrison_nycschools.dagger.ApplicationComponent
import com.example.a2020_05_01_takariharrison_nycschools.dagger.DaggerApplicationComponent

class App: Application() {

    //exposes a global dagger component so that core android classes (Services, Activities, etc) can inject their dependencies
    companion object {
        lateinit var applicationComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.create()
    }
}