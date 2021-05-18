package com.example.tabmenu


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.firebase.auth.FirebaseAuth


class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)


        val redProf = findPreference("pref_redProf") as Preference?
        val passProf = findPreference("pref_changePass") as Preference?
        val exit = findPreference("pref_exit") as Preference?
        exit!!.setOnPreferenceClickListener {
            view?.let { it1 -> (activity as MainActivity).logout(it1) }
            true

        }


        redProf!!.setOnPreferenceClickListener {
            view?.let { it1 -> (activity as MainActivity).adapter(it1, 1) }
            true

        }
        passProf!!.setOnPreferenceClickListener {
            view?.let { it1 -> (activity as MainActivity).adapter(it1, 2) }
            true

        }
    }



}