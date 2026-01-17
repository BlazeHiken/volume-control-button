# Floating Volume Button (Android)

A lightweight Android app that provides a draggable floating button
using an overlay + foreground service which is used to trigger inbuilt
audio slider of android.

## Key Concepts

- Foreground Service (Android 10–14 compliant)
- SYSTEM_ALERT_WINDOW permission handling
- Drag vs Click conflict resolution using scaledTouchSlop
- Accessibility-safe touch handling

## Why this exists

Built to explore correct system-overlay behavior and modern Android
background execution limits.
Plus, my phone's buttons are broken.

## Status

Working prototype / learning project
