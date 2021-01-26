package com.github.braillesystems.learnbraille.ui.screens

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.github.braillesystems.learnbraille.R
import com.github.braillesystems.learnbraille.ui.brailletrainer.BrailleTrainer
import timber.log.Timber

class MyAccessibilityDelegate : View.AccessibilityDelegate() {
    override fun onRequestSendAccessibilityEvent(
        host: ViewGroup?,
        child: View?,
        event: AccessibilityEvent?
    ): Boolean {
        Timber.i("Event: %s", event.toString())
        return super.onRequestSendAccessibilityEvent(host, child, event)
    }
}

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.i("onCreate")
        setContentView(R.layout.activity_main)

        val contentView = findViewById<View>(android.R.id.content)
        contentView.accessibilityDelegate = MyAccessibilityDelegate()

        navController = findNavController(R.id.navHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)

        BrailleTrainer.init(this)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onSupportNavigateUp(): Boolean = try {
        navController.navigateUp()
    } catch (e: IllegalArgumentException) {
        Timber.e(e, "Multitouch navigation")
        false
    }
}
