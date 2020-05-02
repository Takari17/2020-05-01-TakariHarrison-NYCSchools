package com.example.a2020_05_01_takariharrison_nycschools.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.a2020_05_01_takariharrison_nycschools.App
import com.example.a2020_05_01_takariharrison_nycschools.R
import com.example.a2020_05_01_takariharrison_nycschools.dagger.activityViewModelFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_school_sat.*


class SchoolSatFragment : Fragment() {

    private val viewModel by activityViewModelFactory { App.applicationComponent.nycSchoolViewModel }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_school_sat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.schoolData != null) { // this shouldn't be null but just to play it safe...

            schoolName.text = viewModel.schoolData!!.name
            phoneNumber.text = viewModel.schoolData!!.phoneNumber
            city.text = viewModel.schoolData!!.city
            website.text = viewModel.schoolData!!.website
            email.text = viewModel.schoolData!!.email

            viewModel.getSchoolSatData(viewModel.schoolData!!.name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = { satDataList ->

                        if (satDataList.isEmpty()) {
                            readingScore.text = "This school has not documented their SAT scores"
                            writingScore.text = "This school has not documented their SAT scores"
                            mathScore.text = "This school has not documented their SAT scores"
                        } else{
                            readingScore.text = "Reading Score: ${satDataList[0].readingScore}"
                            writingScore.text = "Writing Score: ${satDataList[0].writingScore}"
                            mathScore.text = "Math Score: ${satDataList[0].mathScore}"
                        }
                    },
                    onError = { e ->
                        Log.d("zwi", "Error getting the school SAT data in SchoolSatFragment: $e")
                        viewModel.currentFragment.accept(CurrentFragment.ErrorFrag)
                    }
                )

        } else {
            viewModel.currentFragment.accept(CurrentFragment.ErrorFrag)
        }
    }
}
