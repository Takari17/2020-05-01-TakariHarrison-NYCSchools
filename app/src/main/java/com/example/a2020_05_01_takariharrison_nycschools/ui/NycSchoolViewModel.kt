package com.example.a2020_05_01_takariharrison_nycschools.ui

import androidx.lifecycle.ViewModel
import com.example.a2020_05_01_takariharrison_nycschools.data.NycSchoolRepository
import com.example.a2020_05_01_takariharrison_nycschools.data.SchoolListData
import com.example.a2020_05_01_takariharrison_nycschools.data.SchoolSatData
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Single
import javax.inject.Inject

/*
Allows comms between MainActivity, SchoolListFragment, and SchoolSatFragment. Also chose to stick with
Rx for consistency instead of mixing it with Live Data. Live Data is basically a Behavioral Subject/Relay anyways.
 */
class NycSchoolViewModel @Inject constructor(private val repositoryNyc: NycSchoolRepository) : ViewModel() {

    val currentFragment = PublishRelay.create<CurrentFragment>()
    //will be null until the user selects a school from the recycler view
    var schoolData: SchoolListData? = null


    fun getSchoolListData(): Single<List<SchoolListData>> = repositoryNyc.getSchoolListData()

    fun getSchoolSatData(schoolName: String): Single<List<SchoolSatData>> =
        repositoryNyc.getSchoolSatData(schoolName)

}