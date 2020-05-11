package com.example.a2020_05_01_takariharrison_nycschools

import android.app.Application
import com.example.a2020_05_01_takariharrison_nycschools.dagger.application.ApplicationComponent
import com.example.a2020_05_01_takariharrison_nycschools.dagger.application.DaggerApplicationComponent

class App : Application() {

    companion object {
        lateinit var applicationComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.factory().create(applicationContext)
    }
}