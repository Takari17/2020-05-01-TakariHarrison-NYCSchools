package com.example.a2020_05_01_takariharrison_nycschools.ui.device.phone

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.a2020_05_01_takariharrison_nycschools.R
import com.example.a2020_05_01_takariharrison_nycschools.dagger.activity.ActivityScope
import com.example.a2020_05_01_takariharrison_nycschools.ui.*
import com.example.a2020_05_01_takariharrison_nycschools.ui.device.OnLifecycleEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

@ActivityScope
class PhoneLifecycleEvent @Inject constructor(
    private val savedInstanceState: Bundle?,
    private val supportFragmentManager: FragmentManager,
    private val viewModel: NycSchoolViewModel
) : OnLifecycleEvent {

    private val compositeDisposable = CompositeDisposable()

    //First checks if the Fragment exist to avoid initializing them each time.
    private val schoolSatFragment: Fragment = supportFragmentManager
        .findFragmentByTag(MainActivity.SCHOOL_SAT_FRAG) ?: SchoolSatFragment()

    private val errorFragment: Fragment = supportFragmentManager
        .findFragmentByTag(MainActivity.ERROR_FRAG) ?: ErrorFragment()

    override fun onCreate() {

        //adds initial fragment
        if (savedInstanceState == null) {

            val schoolListFragment: Fragment = supportFragmentManager
                .findFragmentByTag(MainActivity.SCHOOL_LIST_FRAG) ?: SchoolListFragment()

            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, schoolListFragment, MainActivity.SCHOOL_LIST_FRAG)
                .commit()
        }
    }

    override fun onStart() {

        compositeDisposable += viewModel.getCurrentFragment()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { currentFragment ->
                    when (currentFragment) {
                        CurrentFragment.SchoolSatFrag -> {
                            supportFragmentManager.beginTransaction() // didn't stuff this in a method since the animations are different
                                .setCustomAnimations(
                                    R.anim.enter_from_right,
                                    R.anim.exit_to_left
                                )
                                .replace(R.id.container, schoolSatFragment)
                                .addToBackStack(MainActivity.SCHOOL_SAT_FRAG)
                                .commit()
                        }
                        CurrentFragment.ErrorFrag -> {
                            supportFragmentManager.beginTransaction()
                                .setCustomAnimations(
                                    R.anim.enter_from_left,
                                    R.anim.exit_to_right
                                )
                                .replace(R.id.container, errorFragment)
                                .addToBackStack(MainActivity.ERROR_FRAG)
                                .commit()
                        }
                    }
                },
                onError = { e -> Log.d("zwi", "Error observing getCurrentFragment in MainActivity: $e") }
            )
    }

    override fun onStop() {
        compositeDisposable.clear()
    }
}