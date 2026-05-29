# RTCalc

RTCalc is a premium, high-precision calculator application for Android designed with a modern, iPhone-inspired interface. It is optimized to provide a seamless user experience across both mobile phones and tablets.

---

## 🚀 Features

*   **High-Precision Calculations**: Uses `BigDecimal` math operation wrappers internally to eliminate standard floating-point representation bugs (e.g., `0.1 + 0.2` is evaluated exactly as `0.3`).
*   **Persistent Calculation History**: Local database storage logs expressions and results so they can be restored or copied.
*   **Haptic Key Feedback**: Simulates mechanical key presses with physical vibrations (can be toggled on/off in settings).
*   **Adaptive / Responsive Layouts**: Automatically optimizes screen real estate. Mobile layout uses an elegant slide-up history sheet, while tablet and landscape layouts show a persistent side-by-side history panel.
*   **Auto-Scaling Output Display**: Display numbers dynamically shrink in font size to prevent line wrapping or digit cropping.
*   **Copy-to-Clipboard**: Easily copy any calculated result by long-pressing on the display screen.
*   **Theme Engine Support**: Toggle between clean Light theme, pitch-black Dark theme, or bind to the Android System default.

---

## 🛠️ Tech Stack & Architecture

RTCalc is built using modern Android development practices:

*   **Language**: 100% Kotlin
*   **UI Framework**: Jetpack Compose (Material 3 components)
*   **Architecture**: MVVM (Model-View-ViewModel) with Clean Architecture principles
*   **Dependency Injection**: Dagger-Hilt for modularity and testability
*   **Database (History)**: Room Database for local persistent storage
*   **Settings (Preferences)**: Jetpack DataStore (Preferences) for lightweight key-value state persistence (Theme mode, haptics)
*   **State Preservation**: `SavedStateHandle` integration to survive system process deaths and configuration changes (e.g., screen rotations)

---

## 📁 Project Structure

```
RTCalc/
├── app/
│   ├── build.gradle.kts           # App-level build configurations
│   ├── proguard-rules.pro         # Proguard rules for production optimization
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml # Core configuration and activity definitions
│       │   ├── java/com/ritikthakur/rtcalc/
│       │   │   ├── RTCalcApp.kt    # Injected Application context
│       │   │   ├── di/
│       │   │   │   └── AppModule.kt   # Hilt module providing Room DB dependencies
│       │   │   ├── data/
│       │   │   │   ├── local/          # Database definitions, schemas, and queries
│       │   │   │   │   ├── HistoryDao.kt
│       │   │   │   │   ├── HistoryDatabase.kt
│       │   │   │   │   └── HistoryEntity.kt
│       │   │   │   └── repository/     # Repositories encapsulating DB and DataStore
│       │   │   │       ├── HistoryRepository.kt
│       │   │   │       └── SettingsRepository.kt
│       │   │   ├── domain/
│       │   │   │   └── ExpressionEvaluator.kt # Algebraic expression parsing engine
│       │   │   ├── ui/
│       │   │   │   ├── MainActivity.kt # Entry edge-to-edge Activity
│       │   │   │   ├── theme/          # Material 3 colors, typography, and themes
│       │   │   │   │   ├── Color.kt
│       │   │   │   │   ├── Theme.kt
│       │   │   │   │   └── Type.kt
│       │   │   │   ├── view/           # Jetpack Compose layouts and components
│       │   │   │   │   ├── CalculatorScreen.kt
│       │   │   │   │   ├── Display.kt
│       │   │   │   │   ├── HistoryList.kt
│       │   │   │   │   └── Keypad.kt
│       │   │   │   └── viewmodel/      # MVVM ViewModel handling state flows
│       │   │   │       └── CalculatorViewModel.kt
│       │   └── res/                # XML Resources and vectors (adaptive icons)
│       └── test/                   # JVM unit tests for evaluator and viewmodel
│       └── androidTest/            # Android UI instrumentation tests
└── build.gradle.kts                # Project-level plugins setup
└── settings.gradle.kts             # Settings module initialization
```

---

## ⚙️ How it Works (Under the Hood)

### 1. The Expression Engine
Algebraic inputs (e.g. `12 + 3 × 5`) are evaluated in [ExpressionEvaluator.kt](file:///home/crdy/testing/app/RTCalc/app/src/main/java/com/ritikthakur/rtcalc/domain/ExpressionEvaluator.kt) using a custom **Shunting-Yard algorithm**:
1.  **Tokenization**: The input expression is sanitized (e.g., converting `×` to `*` and `÷` to `/`) and tokenized into distinct operands and operators.
2.  **Reverse Polish Notation (RPN)**: Operator precedence (multiplication/division before addition/subtraction) is applied to generate an RPN stack.
3.  **BigDecimal Evaluation**: RPN is computed using Java `BigDecimal` with a context scale of 16 digits, ensuring absolute precision and preventing standard floating-point representation bugs.

### 2. State & Settings Persistence
The active math inputs and calculation outcomes are stored directly inside the [CalculatorViewModel](file:///home/crdy/testing/app/RTCalc/app/src/main/java/com/ritikthakur/rtcalc/ui/viewmodel/CalculatorViewModel.kt)'s `SavedStateHandle`. This ensures that even if Android kills the background process, the display values and history entries are recovered instantly. Persistent settings (such as Dark Theme preferences and Haptic toggles) are written synchronously to **Jetpack DataStore**, rendering reactively when the user adjusts them.

---

## 🛠️ Build & Installation

### Prerequisites
*   Android Studio Hedgehog (2023.1.1) or newer
*   JDK 17 configured in Android Studio
*   Android SDK API 26 or newer (Android 8.0+) configured on your test device

### Build steps (Using Android Studio)
1.  Open Android Studio, select **Open**, and select the `RTCalc` root folder.
2.  Wait for the Gradle sync to finish successfully.
3.  Connect an Android device with USB debugging enabled, or start an Emulator.
4.  Click the green **Run** button to install the debug version on your device.

### Compile steps (Using Gradle wrapper CLI)
Ensure `ANDROID_HOME` env variable points to your Android SDK directory:
```bash
# Clean project and compile the debug APK
./gradlew clean assembleDebug

# Run all unit tests
./gradlew test
```
The compiled output will be located at:
`app/build/outputs/apk/debug/app-debug.apk`
