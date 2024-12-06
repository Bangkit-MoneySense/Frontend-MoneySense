package com.application.moneysense

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.application.moneysense.pageradapter.SectionPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.application.moneysense.data.retrofit.requestPrediction
import com.application.moneysense.data.retrofit.requestHistory
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class MainActivity : AppCompatActivity() {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_1,
            R.string.tab_2,
            R.string.tab_3
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sectionsPagerAdapter = SectionPagerAdapter(this)

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        sectionsPagerAdapter.appName = resources.getString(R.string.app_name)
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabs)

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            val customTab = LayoutInflater.from(this).inflate(R.layout.tab_title, null) as TextView
            customTab.text = getString(TAB_TITLES[position])
            tab.customView = customTab
        }.attach()

        val file = File("/C:/Users/cyber/Videos/pecahan-rp-20000_20150710_204516.jpg")
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val imageBody = MultipartBody.Part.createFormData("input", file.name, requestFile)
        val userIdBody = "1".toRequestBody("text/plain".toMediaTypeOrNull())

        requestPrediction(imageBody, userIdBody,
            onSuccess = { response ->
                val data = response.data
                if (data != null) {
                }
            },
            onError = { errorMessage ->
            }
        )

        requestHistory(1,
            onSuccess = { response ->
                response.data?.forEach {
                }
            },
            onError = { errorMessage ->
            }
        )

        setUpTabCustomizations(tabs, viewPager)
    }

    private fun setUpTabCustomizations(tabs: TabLayout, viewPager: ViewPager2) {
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                for (i in 0 until tabs.tabCount) {
                    val tab = tabs.getTabAt(i)
                    val customView = tab?.customView as? TextView
                    customView?.isSelected = i == position
                }
            }
        })
    }
}
