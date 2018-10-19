package com.example.android.noteapp.utilities

import android.content.Context
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatDelegate


class ThemeUtil{
    companion object {

        /**
         * Looks which theme to pick
         *
         * @param context The context of the Activity calling the function
         * @param delegate The delagate you need to change the themes
         */
        fun setTheme(context: Context, delegate: AppCompatDelegate){
            val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
            val darkTheme = sharedPref.getBoolean("theme_select", false)


            if(darkTheme) enableDarkTheme(delegate) else enableLightTheme(delegate)
        }

        /**
         * Setting the light app theme
         */
        private fun enableLightTheme(delegate: AppCompatDelegate) {
            delegate.setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        /**
         * Setting the dark app theme
         */
        private fun enableDarkTheme(delegate: AppCompatDelegate){
            delegate.setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}