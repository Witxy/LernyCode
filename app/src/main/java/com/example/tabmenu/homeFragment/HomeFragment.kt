package com.example.tabmenu.homeFragment

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import com.example.tabmenu.R


class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        val wb = view.findViewById<WebView>(R.id.wb_compiler)
        val appSettingPrefs: SharedPreferences =this.requireActivity().getSharedPreferences("AppSettingPrefs", 0)

        val isNightModeOn = appSettingPrefs.getBoolean("NightMode", false)
        wb.webViewClient = WebViewClient()
        wb.apply {
            loadUrl("https://rextester.com/")
            settings.javaScriptEnabled = true
            if(isNightModeOn) {
                if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                    WebSettingsCompat.setForceDark(wb.settings, WebSettingsCompat.FORCE_DARK_ON);
                }
            }

        }
        val achieveEditor  = this.requireActivity().getSharedPreferences("achieve", AppCompatActivity.MODE_PRIVATE).edit()
        achieveEditor.putBoolean("achieve4", true)
        achieveEditor.apply()




        return view
    }

    private fun startWebView(url: String) {


    }




}