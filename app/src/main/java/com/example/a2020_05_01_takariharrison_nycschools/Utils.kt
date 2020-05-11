package com.example.a2020_05_01_takariharrison_nycschools

import android.content.res.Configuration
import android.content.res.Resources
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.a2020_05_01_takariharrison_nycschools.ui.device.DeviceType

/**
Returns a lazy view model reference scoped to the Activity of a Fragment.
Used so Dagger can inject ViewModels with args.
 */
inline fun <reified T : ViewModel> Fragment.activityViewModelFactory(
    crossinline provider: () -> T
) = activityViewModels<T> {
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            provider() as T
    }
}

fun deviceType(resources: Resources): DeviceType {
    val screenLayout = resources.configuration.screenLayout
    return when {
        screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == Configuration.SCREENLAYOUT_SIZE_SMALL
                || screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == Configuration.SCREENLAYOUT_SIZE_NORMAL -> DeviceType.Phone
        else -> DeviceType.Tablet
    }
}