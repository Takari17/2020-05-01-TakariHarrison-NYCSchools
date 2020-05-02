package com.example.a2020_05_01_takariharrison_nycschools.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.a2020_05_01_takariharrison_nycschools.App
import com.example.a2020_05_01_takariharrison_nycschools.R
import com.example.a2020_05_01_takariharrison_nycschools.dagger.activityViewModelFactory
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy

class MainActivity : AppCompatActivity() {

    companion object {
        const val SCHOOL_LIST_FRAG = "school list fragment"
        const val SCHOOL_SAT_FRAG = "school sat fragment"
        const val ERROR_FRAG = "error fragment"
    }

    //First checks if the Fragment exist to avoid initializing them each time.
    private val schoolSatFragment: Fragment = supportFragmentManager
        .findFragmentByTag(SCHOOL_SAT_FRAG) ?: SchoolSatFragment()

    private val errorFragment: Fragment = supportFragmentManager
        .findFragmentByTag(ERROR_FRAG) ?: ErrorFragment()

    private val viewModel by activityViewModelFactory { App.applicationComponent.nycSchoolViewModel }

    private val compositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.navigationBarColor = Color.parseColor("#555454")


        //adds initial fragment
        if (savedInstanceState == null) {

            val schoolListFragment: Fragment = supportFragmentManager
                .findFragmentByTag(SCHOOL_LIST_FRAG) ?: SchoolListFragment()

            supportFragmentManager.beginTransaction()
                .add(R.id.container, schoolListFragment, SCHOOL_LIST_FRAG)
                .commit()
        }
    }

    override fun onStart() {
        super.onStart()

        compositeDisposable += viewModel.currentFragment.subscribeBy(
            onNext = { currentFragment ->
                when (currentFragment) {

                    CurrentFragment.SchoolSatFrag -> {
                        supportFragmentManager.beginTransaction() // didn't stuff this in a method since the animations are different
                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                            .replace(R.id.container, schoolSatFragment)
                            .addToBackStack(SCHOOL_SAT_FRAG)
                            .commit()
                    }
                    CurrentFragment.ErrorFrag -> {
                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right)
                            .replace(R.id.container, errorFragment)
                            .addToBackStack(ERROR_FRAG)
                            .commit()
                    }
                }
            },
            onError = { e ->
                Log.d("zwi", "Error observing getCurrentFragment in MainActivity: $e")
            }
        )
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }
}
