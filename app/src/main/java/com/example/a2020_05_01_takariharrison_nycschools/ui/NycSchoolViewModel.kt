package com.example.a2020_05_01_takariharrison_nycschools.ui

import androidx.lifecycle.ViewModel
import com.example.a2020_05_01_takariharrison_nycschools.data.NycSchoolRepository
import com.example.a2020_05_01_takariharrison_nycschools.data.SchoolListData
import com.example.a2020_05_01_takariharrison_nycschools.data.SchoolSatData
import com.example.a2020_05_01_takariharrison_nycschools.ui.device.DeviceType
import com.example.a2020_05_01_takariharrison_nycschools.ui.device.tablet.AnimState
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NycSchoolViewModel @Inject constructor(private val repositoryNyc: NycSchoolRepository) :
    ViewModel() {

    private val currentFragment = PublishRelay.create<CurrentFragment>()
    private val animState = PublishRelay.create<AnimState>()
    private val schoolData = BehaviorRelay.create<SchoolListData>()

    fun getSchoolListData(): Single<List<SchoolListData>> = repositoryNyc.getSchoolListData()

    fun getSchoolSatData(schoolName: String): Observable<List<SchoolSatData>> =
        repositoryNyc.getSchoolSatData(schoolName)

    fun adapterItemClick(deviceType: DeviceType, schoolListData: SchoolListData) {
        schoolData.accept(schoolListData)

        if (deviceType == DeviceType.Phone)
            currentFragment.accept(CurrentFragment.SchoolSatFrag)
        else
            animState.accept(AnimState.MasterDetailed)
    }

    fun onError(deviceType: DeviceType) {
        if (deviceType == DeviceType.Phone)
            currentFragment.accept(CurrentFragment.ErrorFrag)
        else
            animState.accept(AnimState.Error)
    }

    fun getCurrentFragment(): Observable<CurrentFragment> = currentFragment
    fun getAnimState(): Observable<AnimState> = animState
    fun getSchoolData(): Observable<SchoolListData> = schoolData
}
