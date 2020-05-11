package com.example.a2020_05_01_takariharrison_nycschools.ui.device.tablet

import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.util.Log
import android.view.animation.AnticipateOvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.example.a2020_05_01_takariharrison_nycschools.R
import com.example.a2020_05_01_takariharrison_nycschools.dagger.activity.ActivityScope
import com.example.a2020_05_01_takariharrison_nycschools.ui.NycSchoolViewModel
import com.example.a2020_05_01_takariharrison_nycschools.ui.device.OnLifecycleEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

@ActivityScope
class TabletLifecycleEvent @Inject constructor(
    private val masterDetailedConstraints: ConstraintSet,
    private val errorConstraints: ConstraintSet,
    private val activity: AppCompatActivity,
    private val viewModel: NycSchoolViewModel,
    private val currentLayout: ConstraintLayout
) : OnLifecycleEvent {

    private val compositeDisposable = CompositeDisposable()

    private val defaultTransition = ChangeBounds().apply {
        interpolator = AnticipateOvershootInterpolator(.5f)
        duration = 1800
    }

    override fun onCreate() {
        masterDetailedConstraints.clone(activity, R.layout.tablet_school_detailed_layout)
        errorConstraints.clone(activity, R.layout.tablet_error_layout)
    }

    override fun onStart() {
        compositeDisposable += viewModel.getAnimState()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { animState ->
                    when(animState){
                        AnimState.MasterDetailed -> startDetailedAnim()
                        AnimState.Error ->  startErrorAnim()
                    }
                },
                onError = { e ->
                    startErrorAnim()
                    Log.d("zwi", "Error observing getAnimState() in TabletLifecycleEvent: $e")
                }
            )
    }

    override fun onStop() {
        compositeDisposable.clear()
    }

    private fun startDetailedAnim() {
        TransitionManager.beginDelayedTransition(currentLayout, defaultTransition)
        masterDetailedConstraints.applyTo(currentLayout)
    }

    private fun startErrorAnim() {
        TransitionManager.beginDelayedTransition(currentLayout, defaultTransition)
        errorConstraints.applyTo(currentLayout)
    }
}
