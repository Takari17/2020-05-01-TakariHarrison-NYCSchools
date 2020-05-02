package com.example.a2020_05_01_takariharrison_nycschools.dagger

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

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

/**
Returns a lazy view model reference to an Activity scoped to itself.
Used so Dagger can inject ViewModels with args.
 */
inline fun <reified T : ViewModel> AppCompatActivity.activityViewModelFactory(
    crossinline provider: () -> T
) = viewModels<T> {
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            provider() as T
    }
}