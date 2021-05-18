package com.example.tabmenu.homeFragment

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
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
        wb.webViewClient = WebViewClient()
        wb.apply {
            loadUrl("https://rextester.com/")
            settings.javaScriptEnabled = true
        }





        return view
    }

    private fun startWebView(url: String) {


    }




}