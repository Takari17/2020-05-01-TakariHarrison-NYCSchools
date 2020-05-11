package com.example.a2020_05_01_takariharrison_nycschools.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.a2020_05_01_takariharrison_nycschools.App
import com.example.a2020_05_01_takariharrison_nycschools.R
import com.example.a2020_05_01_takariharrison_nycschools.activityViewModelFactory
import com.example.a2020_05_01_takariharrison_nycschools.deviceType
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_school_sat.*


class SchoolSatFragment : Fragment() {

    private val viewModel by activityViewModelFactory { App.applicationComponent.nycSchoolViewModel }
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_school_sat, container, false)
    }

    override fun onStart() {
        super.onStart()

        compositeDisposable += viewModel.getSchoolData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { schoolData ->
                schoolName.text = schoolData.name
                phoneNumber.text = schoolData.phoneNumber
                city.text = schoolData.city
                website.text = schoolData.website
                email.text = schoolData.email
                schoolData
            }
            .observeOn(Schedulers.io())
            .flatMap { schoolData ->
                viewModel.getSchoolSatData(schoolData.name)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { satDataList ->

                    if (satDataList.isEmpty()) {
                        readingScore.text = "This school has not documented their SAT scores"
                        writingScore.text = "This school has not documented their SAT scores"
                        mathScore.text = "This school has not documented their SAT scores"
                    } else {
                        readingScore.text = "Reading Score: ${satDataList[0].readingScore}"
                        writingScore.text = "Writing Score: ${satDataList[0].writingScore}"
                        mathScore.text = "Math Score: ${satDataList[0].mathScore}"
                    }
                },
                onError = { e ->
                    Log.d("zwi", "Error getting the school SAT data in SchoolSatFragment: $e")
                    viewModel.onError(deviceType(resources))
                }
            )
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }
}
