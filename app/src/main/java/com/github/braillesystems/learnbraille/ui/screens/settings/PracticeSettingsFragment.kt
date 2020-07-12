package com.github.braillesystems.learnbraille.ui.screens.settings

import android.os.Bundle
import com.github.braillesystems.learnbraille.R
import com.github.braillesystems.learnbraille.utils.title

class PracticeSettingsFragment : androidx.preference.PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_practice, rootKey)
        title = getString(R.string.menu_title_decks_list)
    }
}