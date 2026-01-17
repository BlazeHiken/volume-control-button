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
    private lateinit var statusText: android.widget.TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toggleButton = findViewById(R.id.toggleButton)
        statusText = findViewById(R.id.statusText)

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
        if (disabled) {
            toggleButton.setText(R.string.permission_required)
            statusText.setText(R.string.status_inactive)
            toggleButton.isEnabled = true 
            return
        }

        if (isButtonActive) {
            toggleButton.setText(R.string.disable_button)
            statusText.setText(R.string.status_active)
            statusText.setTextColor(android.graphics.Color.parseColor("#4CAF50")) // Active color (Green)
        } else {
            toggleButton.setText(R.string.enable_button)
            statusText.setText(R.string.status_inactive)
            statusText.setTextColor(getColor(android.R.color.darker_gray))
        }
        toggleButton.isEnabled = true
    }
}
