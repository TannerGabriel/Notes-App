package com.example.android.noteapp.settings

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceActivity
import android.widget.Toast
import com.example.android.noteapp.MainActivity
import com.example.android.noteapp.R
import com.example.android.noteapp.utilities.ThemeUtil


class SettingsActivity : PreferenceActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key.equals("theme_select")) {
            Toast.makeText(this, "Theme will change the next time you go back to the mainscreen!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.settings_preference)
    }


    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences
                .registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences
                .unregisterOnSharedPreferenceChangeListener(this)
    }
}
