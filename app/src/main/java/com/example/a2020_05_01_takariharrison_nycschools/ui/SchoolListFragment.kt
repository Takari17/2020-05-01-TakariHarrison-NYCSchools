package com.example.a2020_05_01_takariharrison_nycschools.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a2020_05_01_takariharrison_nycschools.App.Companion.applicationComponent
import com.example.a2020_05_01_takariharrison_nycschools.R
import com.example.a2020_05_01_takariharrison_nycschools.activityViewModelFactory
import com.example.a2020_05_01_takariharrison_nycschools.deviceType
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_school_list.*

class SchoolListFragment : Fragment() {

    private val viewModel by activityViewModelFactory { applicationComponent.nycSchoolViewModel }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_school_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        schoolListRecyclerView.visibility = View.INVISIBLE

        //fetches the school list data from the API then populates the recycler view with the results.
        viewModel.getSchoolListData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { schoolListData ->

                    val schoolListAdapter = SchoolListAdapter(schoolListData) { selectedItemIndex ->
                        viewModel.adapterItemClick(
                            deviceType(resources),
                            schoolListData[selectedItemIndex]
                        )
                    }

                    schoolListRecyclerView.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(context)
                        adapter = schoolListAdapter
                    }

                    exitLoadingState()
                },
                onError = { e ->
                    Log.d("zwi", "Error getting school list data in SchoolListFragment: $e")
                    viewModel.onError(deviceType(resources))
                }
            )
    }

    private fun exitLoadingState() {
        schoolListRecyclerView.visibility = View.VISIBLE
        progressBar.visibility = View.INVISIBLE
    }
}
