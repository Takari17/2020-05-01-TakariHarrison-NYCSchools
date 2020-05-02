package com.example.a2020_05_01_takariharrison_nycschools.dagger

import com.example.a2020_05_01_takariharrison_nycschools.data.NycSchoolAPI
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
object ApplicationModule {

    @JvmStatic
    @Provides
    fun provideRetrofit(): NycSchoolAPI {
        return Retrofit.Builder()
            .baseUrl(NycSchoolAPI.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NycSchoolAPI::class.java)
    }
}