package ru.spbstu.amd.learnbraille.screens.lessons

import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.braille_dots.*
import kotlinx.android.synthetic.main.inner_lesson_info.*
import kotlinx.android.synthetic.main.inner_lesson_info.view.*
import kotlinx.android.synthetic.main.inner_lesson_symbol.*
import kotlinx.android.synthetic.main.inner_lesson_symbol.view.*
import kotlinx.android.synthetic.main.fragment_lesson_step.*
import kotlinx.android.synthetic.main.fragment_lesson_step.view.*
import ru.spbstu.amd.learnbraille.R
import androidx.navigation.fragment.findNavController
import ru.spbstu.amd.learnbraille.database.*

interface LessonStep {
    abstract fun show()
    abstract fun getTitle(): String
}

// TODO refactor
class LessonStepFragment : Fragment() {
    companion object {
        private var currentStep = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(
        R.layout.fragment_lesson_step,
        container,
        false
    ).apply {

        setHasOptionsMenu(true)

        lesson_info.layoutResource = R.layout.inner_lesson_info
        lesson_symbol.layoutResource = R.layout.inner_lesson_symbol

        val stubTextView = lesson_info.inflate()
        val stubShowView = lesson_symbol.inflate()

        class TextStep(
            val nLesson: Int = 1,
            val nStep: Int = 1,
            val longText: String = "",
            val stepTitle: String = "Прочтите текст"
        ) : LessonStep {
            override fun show() {
                textView.text = this.longText
                stubTextView.visibility = VISIBLE
                stubShowView.visibility = GONE
            }

            override fun getTitle() = this.stepTitle
        }

        val dotCheckBoxes = arrayOf(
            dotButton1, dotButton2, dotButton3,
            dotButton4, dotButton5, dotButton6
        )

        class ShowStep(
            val nLesson: Int = 1,
            val nStep: Int = 1,
            val bigText: Char = 'А',
            val brailleDots: BooleanArray = booleanArrayOf(true, false, false, false, false, false),
            val stepTitle: String = "Ознакомьтесь с буквой"
        ) : LessonStep {

            override fun show() {
                letter.text = bigText.toString()
                for (i in dotCheckBoxes.indices) {
                    dotCheckBoxes[i].isClickable = false
                    dotCheckBoxes[i].setOnCheckedChangeListener { _, _ -> }
                    dotCheckBoxes[i].isChecked = brailleDots[i]
                }
                stubShowView.visibility = VISIBLE
                stubTextView.visibility = GONE
            }

            override fun getTitle() = this.stepTitle
        }


        class InputStep(
            val nLesson: Int = 1,
            val nStep: Int = 1,
            val bigText: Char = ' ',
            val brailleDots: BooleanArray = booleanArrayOf(
                false,
                false,
                false,
                false,
                false,
                false
            ),
            val stepTitle: String = "Введите букву"
        ) : LessonStep {

            var checked = booleanArrayOf(false, false, false, false, false, false)

            override fun show() {
                letter.text = bigText.toString()
                checked = booleanArrayOf(false, false, false, false, false, false)
                for (i in dotCheckBoxes.indices) {
                    dotCheckBoxes[i].isChecked = false
                    dotCheckBoxes[i].isClickable = true
                    dotCheckBoxes[i].setOnCheckedChangeListener { _, isChecked ->
                        checked[i] = isChecked
                        if (checked contentEquals brailleDots)
                            Toast.makeText(context, "правильно", Toast.LENGTH_SHORT).show()
                    }
                }
                stubTextView.visibility = GONE
                stubShowView.visibility = VISIBLE
            }

            override fun getTitle() = this.stepTitle
        }

        val steps:Array<LessonStep> = arrayOf(
            // here lessons are shortened (after all, it's just a demo).
            // See https://github.com/braille-systems/learn-braille/wiki/android-lessons-curriculum
            TextStep(1, 1, resources.getString(R.string.text_step1), "Знакомство с шеститочием"),
            ShowStep(1, 2, ' ', booleanArrayOf(true, true, true, true, true, true), "Шеститочие"),
            InputStep(1, 3, ' ', booleanArrayOf(true, true, true, true, true, true), "Введите все шесть точек"),
            TextStep(1, 4, resources.getString(R.string.text_step2), "Работа с букварём"),
            TextStep(1, 5, resources.getString(R.string.text_step2a)),
            ShowStep(1, 6, ' ', booleanArrayOf(true, false, true, true, false, false), "точки 1, 3, 4"),
            ShowStep(1, 7, ' ', booleanArrayOf(true, false, false, false, true, true), "точки 1, 5, 6"),
            ShowStep(1, 8, ' ', booleanArrayOf(false, true, true, true, false, true), "точки 2, 3, 4, 6"),
            InputStep(1, 9, ' ', booleanArrayOf(true, false, true, true, false, false), "Введите точки 1, 3, 4"),
            InputStep(1, 10, ' ', booleanArrayOf(false, true, false, true, true, false), "Введите точки 2, 4, 5"),
            InputStep(1, 11, ' ', booleanArrayOf(false, true, false, false, true, true), "Введите точки 2, 5, 6"),
            TextStep(2, 1, resources.getString(R.string.text_step3)),
            TextStep(2, 2, resources.getString(R.string.text_step4)),
            ShowStep(2, 3, 'А', booleanArrayOf(true, false, false, false, false, false)),
            TextStep(2, 4, resources.getString(R.string.text_step5), "Работа с букварём"),
            InputStep(2, 5, 'А', booleanArrayOf(true, false, false, false, false, false)),
            TextStep(2, 6, resources.getString(R.string.text_step6)),
            ShowStep(2, 7, 'Б', booleanArrayOf(true, true, false, false, false, false)),
            InputStep(2, 8, 'Б', booleanArrayOf(true, true, false, false, false, false))
        )

        val navigateFwd = View.OnClickListener {
            currentStep++
            currentStep %= steps.size
            steps[currentStep].show()
            (activity as AppCompatActivity).supportActionBar?.title = steps[currentStep].getTitle()
        }

        val navigateBack = View.OnClickListener {
            if (currentStep > 0) currentStep--
            steps[currentStep].show()
            (activity as AppCompatActivity).supportActionBar?.title = steps[currentStep].getTitle()
        }

        next_button.setOnClickListener(navigateFwd)
        prev_button.setOnClickListener(navigateBack)

        steps[currentStep].show()
        (activity as AppCompatActivity).supportActionBar?.title = steps[currentStep].getTitle()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.help_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = super.onOptionsItemSelected(item).also {
        when (item.itemId) {
            R.id.help -> {
                val action = LessonStepFragmentDirections.actionLessonStepFragmentToHelpFragment()
                action.helpMessage = resources.getString(R.string.lessons_help_common)
                findNavController().navigate(action)
            }
        }
    }
}
