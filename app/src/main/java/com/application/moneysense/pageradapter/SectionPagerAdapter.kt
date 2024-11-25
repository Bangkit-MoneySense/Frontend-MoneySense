package com.application.moneysense.pageradapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.application.moneysense.ui.camerafragment.CameraFragment
import com.application.moneysense.ui.microphonefragment.MicrophoneFragment
import com.application.moneysense.ui.realtimefragment.RealtimeCamFragment

class SectionPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var appName: String = ""

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        // link fragment to activity based on position
        return when (position) {
            0 -> MicrophoneFragment()
            1 -> CameraFragment()
            2 -> RealtimeCamFragment()
            else -> MicrophoneFragment()
        }
    }
}