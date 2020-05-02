package com.example.a2020_05_01_takariharrison_nycschools.dagger

import com.example.a2020_05_01_takariharrison_nycschools.data.NycSchoolRepository
import com.example.a2020_05_01_takariharrison_nycschools.ui.NycSchoolViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent { // explain why this is global
    val nycSchoolViewModel: NycSchoolViewModel
    val nycSchoolRepository: NycSchoolRepository
}