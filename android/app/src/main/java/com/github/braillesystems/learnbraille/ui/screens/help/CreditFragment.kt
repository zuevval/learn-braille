package com.github.braillesystems.learnbraille.ui.screens.help

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.parseAsHtml
import androidx.databinding.DataBindingUtil
import com.github.braillesystems.learnbraille.R
import com.github.braillesystems.learnbraille.databinding.FragmentHelpBinding
import com.github.braillesystems.learnbraille.utils.checkedAnnounce
import com.github.braillesystems.learnbraille.utils.getStringArg
import com.github.braillesystems.learnbraille.utils.removeHtmlMarkup
import com.github.braillesystems.learnbraille.utils.title

class CreditFragment : HelpFragment() {

    private val helpMessageArgName = "help_message"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = DataBindingUtil.inflate<FragmentHelpBinding>(
        inflater,
        R.layout.fragment_help,
        container,
        false
    ).apply {
        val content = getStringArg(helpMessageArgName)
        helpMessage.text = content.parseAsHtml()
        helpMessage.movementMethod = ScrollingMovementMethod()
        checkedAnnounce(content.removeHtmlMarkup())
        title = getString(R.string.credit_title)
        helpMessage.movementMethod = LinkMovementMethod.getInstance();
    }.root

}