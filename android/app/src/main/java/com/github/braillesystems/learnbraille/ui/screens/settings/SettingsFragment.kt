package com.github.braillesystems.learnbraille.ui.screens.settings

import android.os.Bundle
import androidx.preference.Preference
import com.github.braillesystems.learnbraille.R
import com.github.braillesystems.learnbraille.ui.screens.help.CreditFragmentDirections
import com.github.braillesystems.learnbraille.utils.navigate
import com.github.braillesystems.learnbraille.utils.title

class SettingsFragment : androidx.preference.PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_hierarchy, rootKey)
        title = getString(R.string.preferences_actionbar_title)
    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        if (preference != null) {
            if (preference.title == getString(R.string.credit_title)) {
                val action = CreditFragmentDirections.actionGlobalCreditFragment()
                action.helpMessage = "credit!"
                navigate(action)
            }
        }
        return super.onPreferenceTreeClick(preference)
    }
}