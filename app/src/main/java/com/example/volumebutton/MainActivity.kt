package com.example.volumebutton

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var isButtonActive = false
    private lateinit var toggleButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toggleButton = Button(this)
        setContentView(toggleButton)

        toggleButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                !Settings.canDrawOverlays(this)
            ) {
                requestOverlayPermission()
            } else {
                toggleFloatingButton()
            }
        }

        updateButtonText()
    }

    override fun onResume() {
        super.onResume()

        // User may have granted permission in Settings
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
            !Settings.canDrawOverlays(this)
        ) {
            updateButtonText(disabled = true)
        } else {
            updateButtonText()
        }
    }

    private fun requestOverlayPermission() {
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:$packageName")
        )
        startActivity(intent)
    }

    private fun toggleFloatingButton() {
        if (isButtonActive) {
            stopService(Intent(this, FloatingButtonService::class.java))
            isButtonActive = false
        } else {
            startService(Intent(this, FloatingButtonService::class.java))
            isButtonActive = true
        }
        updateButtonText()
    }

    private fun updateButtonText(disabled: Boolean = false) {
        toggleButton.text = when {
            disabled -> "Enable overlay permission first"
            isButtonActive -> "Disable Floating Button"
            else -> "Enable Floating Button"
        }
    }
}
