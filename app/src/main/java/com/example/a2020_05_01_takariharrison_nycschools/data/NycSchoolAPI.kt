package com.example.a2020_05_01_takariharrison_nycschools.data

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NycSchoolAPI {

    companion object {
        const val BASE_URL = "https://data.cityofnewyork.us"
    }

    @GET("https://data.cityofnewyork.us/resource/s3k6-pzi2.json")
    fun getNycSchoolListData(): Single<List<SchoolListData>>

    //school name must be in caps for whatever reason
    @GET("https://data.cityofnewyork.us/resource/f9bf-2cp4.json")
    fun getSchoolSatData(@Query("school_name") schoolNameInCaps: String): Single<List<SchoolSatData>>
}
