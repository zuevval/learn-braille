package ru.spbstu.amd.learnbraille.screens

import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Fragment.updateTitle(title: String) {
    (activity as AppCompatActivity)
        .supportActionBar
        ?.title = title
}

fun makeUnchecked(checkBoxes: Array<CheckBox>) = checkBoxes
    .forEach {
        if (it.isChecked) {
            it.toggle()
        }
    }
