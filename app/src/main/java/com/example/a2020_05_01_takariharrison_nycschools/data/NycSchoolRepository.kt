package com.example.a2020_05_01_takariharrison_nycschools.data

import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

/*
For a small scale project a repo is useless as it has no data filtering logic to handle. But as the project grows it will
be built to scale. Same with the NycSchoolViewModel.
 */
@Singleton //Talks to multiple View Models so it's best to make it a singleton.
class NycSchoolRepository @Inject constructor(
    private val nycSchoolAPI: NycSchoolAPI
) {

    fun getSchoolListData(): Single<List<SchoolListData>> = nycSchoolAPI.getNycSchoolListData()

    fun getSchoolSatData(schoolName: String): Single<List<SchoolSatData>> =
        nycSchoolAPI.getSchoolSatData(schoolName.toUpperCase())
}