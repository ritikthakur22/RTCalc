# RTCalc

RTCalc is a premium, high-precision scientific calculator application for Android designed with a modern, sleek interface. It is optimized to provide a seamless user experience across both mobile phones and tablets.

---

## Screenshots

*(Screenshots showing the portrait layout, expanded scientific panel, and landscape scientific grid)*

---

## Features

- **High-Precision Calculations**: Eliminates floating-point representation errors for perfect decimal calculations.
- **Display Cursor Editing**: Tap any location in the expression to edit, insert, or delete characters using selection handles and a blinking cursor.
- **Extra Editing Features**: Full support for Copy, Paste, Select All, Undo, and Redo.
- **Persistent Calculation History**: Local storage logs expressions and results so they can be easily restored or copied.
- **Haptic Key Feedback**: Mechanical key vibrations on press (can be toggled in settings) to provide realistic tactile feedback.
- **Adaptive / Responsive Layouts**: Standard phone keypad expands into a collapsible scientific panel on swipe-up or button press, while landscape/tablet modes display a full-grid layout.
- **Auto-Scaling Output Display**: Display numbers dynamically shrink to prevent line wrapping or digit cropping.

---

## Scientific Features

RTCalc supports a wide range of advanced scientific operations:

- **Trigonometry**: `sin`, `cos`, `tan`, `asin`, `acos`, `atan` (supporting both Degrees and Radians angle modes).
- **Hyperbolic**: `sinh`, `cosh`, `tanh`.
- **Logarithms**: `log` (base 10), `ln` (natural log).
- **Basic Scientific**: Square root (`√`), `x²`, `x³`, `xʸ` (powers), `1/x` (reciprocals), `n!` (factorials), and absolute value (`abs`).
- **Memory Operations**: `MC` (Memory Clear), `MR` (Memory Recall), `MS` (Memory Store), `M+` (Memory Add), `M-` (Memory Subtract).
- **Constants**: Pi (`π`), Euler's constant (`e`), and Golden Ratio (`φ`).
- **Advanced Math**: `nPr` (permutations), `nCr` (combinations), `Rand` (random decimal), `mod` (modulo), `floor`, and `ceil`.

---

## Installation

RTCalc requires an Android device running Android 8.0+ (API 26 or above). 

1. Download the latest compiled `app-debug.apk` or `app-release.aab` from the build outputs.
2. Transfer the package to your test device.
3. Open the file on your device and install the application (allow installation from unknown sources if prompted).

---

## Build Instructions

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or newer
- JDK 17 configured in Android Studio path
- Android SDK API 34 installed

### Build Steps
You can compile the app using the Gradle wrapper CLI:

```bash
# Set Android SDK path
export ANDROID_HOME=/home/crdy/Android/Sdk

# Clean and compile the debug APK
./gradlew clean assembleDebug

# Compile the release App Bundle (AAB)
./gradlew bundleRelease

# Run all unit tests
./gradlew test
```

Build outputs will be generated under:
- Debug APK: `app/build/outputs/apk/debug/app-debug.apk`
- Release AAB: `app/build/outputs/bundle/release/app-release.aab`

---

## Changelog

### v1.4.0

Added:
- Redesigned scientific panel using an interactive Modal Bottom Sheet to ensure the display is always pinned and fully visible.

Fixed:
- Restored hardware-level haptic feedback using Android's native view haptics for perfectly responsive keypresses.
- Fixed expression display being compressed when expanding the scientific panel in portrait mode.

### v1.3.0

Added:
- Expandable portrait scientific panel
- Degree/Radian mode direct toggle
- Memory operations
- Expression cursor editing (cursor blinking, selection, copy, paste, select all)
- Editing helper features (Undo and Redo actions)

Fixed:
- Pinned display layout to prevent SCI panel from covering it
- Device hardware haptic feedback vibration on button clicks
- Release package R8 compiler optimization rule

### v1.2.0

Added:
- Calculation history search and item-level deletion

### v1.0.0

Added:
- Basic standard calculator arithmetic engine
- Dark / Light / System theme selector
- Settings persistence

---

## Version History

- **v1.4.0**: Completely redesigned scientific panel layout and stabilized haptic feedback.
- **v1.3.0**: Current active build incorporating full scientific features and editing cursor.
- **v1.2.0**: Integrated searchable database calculation history.
- **v1.0.0**: Baseline standard calculator layout.

---

## License

This project is licensed under the MIT License.

---

## Contact

For support, feedback, or inquiries, please contact:
**Ritik Thakur** - [ritikthakur@duck.com](mailto:ritikthakur@duck.com)
