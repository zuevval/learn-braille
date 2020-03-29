package ru.spbstu.amd.learnbraille.screens.lessons

import android.os.Bundle
import android.view.*
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.braille_dots.*
import kotlinx.android.synthetic.main.inner_lesson_info.*
import ru.spbstu.amd.learnbraille.R
import ru.spbstu.amd.learnbraille.database.*
import ru.spbstu.amd.learnbraille.databinding.FragmentLessonStepBinding
import ru.spbstu.amd.learnbraille.databinding.InnerLessonInfoBindingImpl
import ru.spbstu.amd.learnbraille.databinding.InnerLessonSymbolBinding
import ru.spbstu.amd.learnbraille.screens.updateTitle

class LessonStepFragment : Fragment() {

    private lateinit var viewModel: LessonStepViewModel

    private val DefaultUserId = 1L

    private val title: String
        get() = when (viewModel.currentLessonStep.value!!.step.data) {
            is Info -> getString(R.string.lessons_title_info)
            is LastInfo -> getString(R.string.lessons_title_last_info)
            is InputSymbol -> getString(R.string.lessons_title_input_symbol)
            is InputDots -> getString(R.string.lessons_title_input_dots)
            is ShowSymbol -> getString(R.string.lessons_title_show_symbol)
            is ShowDots -> getString(R.string.lessons_title_show_dots)
        }

    private val helpMessage: String
        get() = getString(R.string.lessons_help_template).format(
            getString(R.string.lessons_help_common),
            when (viewModel.currentLessonStep.value!!.step.data) {
                is Info -> getString(R.string.lessons_help_info)
                is LastInfo -> getString(R.string.lessons_help_last_info)
                is InputSymbol -> getString(R.string.lessons_help_input_symbol)
                is InputDots -> getString(R.string.lessons_help_input_dots)
                is ShowSymbol -> getString(R.string.lessons_help_show_symbol)
                is ShowDots -> getString(R.string.lessons_help_show_dots)
            }
        )

    //private lateinit var dotCheckBoxes: Array<CheckBox>
    private lateinit var bigLetter: TextView
    private lateinit var infoTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = DataBindingUtil.inflate<FragmentLessonStepBinding>(
        inflater,
        R.layout.fragment_lesson_step,
        container,
        false
    ).apply {

        val application = requireNotNull(activity).application
        val database = LearnBrailleDatabase.getInstance(application)
        val viewModelFactory = LessonStepViewModelFactory(
            application, DefaultUserId, database.stepDao, database.userPassedStepDao
        ) {
            null
        }
        viewModel = ViewModelProvider(
            this@LessonStepFragment, viewModelFactory
        ).get(LessonStepViewModel::class.java)

        lessonStepViewModel = viewModel
        lifecycleOwner = this@LessonStepFragment

        viewModel.currentLessonStep.observe(this@LessonStepFragment, Observer {
            updateTitle(title)
        })

        setHasOptionsMenu(true)

        DataBindingUtil.inflate<InnerLessonSymbolBinding>(
            inflater,
            R.layout.inner_lesson_symbol,
            container,
            false
        ).apply {
            /*dotCheckBoxes = lessonDots.run {
                arrayOf(
                    dotButton1, dotButton2, dotButton3,
                    dotButton4, dotButton5, dotButton6
                )
            }*/
            bigLetter = letter
        }

        DataBindingUtil.inflate<InnerLessonInfoBindingImpl>(
            inflater,
            R.layout.inner_lesson_info,
            container,
            false
        ).apply {
            infoTextView = textView
        }

        fun showStep(step: LiveData<LessonWithStep>) {
            lessonSymbol.viewStub?.isVisible = true;
            lessonInfo.viewStub?.isVisible = false;
            /*if (step.value!!.step.data is ShowDots || step.value!!.step.data is ShowSymbol) {
                lessonSymbol.viewStub?.isVisible = true;
                lessonInfo.viewStub?.isVisible = false;
                /*dotCheckBoxes.forEach {
                    it.isClickable = true
                    it.setOnCheckedChangeListener { _, _ ->
                        // TODO check input, make toast if correct
                        //viewModel.checkInput(data.symbol.brailleDots) // doesn't work
                    }
                }*/
            }*/
            // TODO update view in case of other step.value.step.data type
        }
        nextButton.setOnClickListener {
            viewModel.nextStep()
            showStep(viewModel.currentLessonStep)
        }
        prevButton.setOnClickListener {
            viewModel.prevStep()
            showStep(viewModel.currentLessonStep)
        }

        showStep(viewModel.currentLessonStep)
    }.root

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.help_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = super.onOptionsItemSelected(item).also {
        when (item.itemId) {
            R.id.help -> {
                val action = LessonStepFragmentDirections.actionLessonStepFragmentToHelpFragment()
                action.helpMessage = helpMessage
                findNavController().navigate(action)
            }
        }
    }

    private fun makeUnchecked(checkBoxes: Array<CheckBox>) = checkBoxes
        .forEach {
            if (it.isChecked) {
                it.toggle()
            }
        }

    companion object {
        val CORRECT_BUZZ_PATTERN = longArrayOf(100, 100, 100, 100, 100, 100)
        val INCORRECT_BUZZ_PATTERN = longArrayOf(0, 200)
    }
}
