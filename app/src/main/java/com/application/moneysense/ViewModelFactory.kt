package com.application.moneysense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.application.moneysense.data.pref.UserPreferences
import com.application.moneysense.ui.moneyscan.MoneyScanViewModel

class ViewModelFactory(private val pref: UserPreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoneyScanViewModel::class.java)) {
            return MoneyScanViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}