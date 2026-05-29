package com.ritikthakur.rtcalc.ui.view

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.HapticFeedbackConstants
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ritikthakur.rtcalc.ui.theme.*

private fun triggerVibration(context: Context) {
    try {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        if (vibrator != null && vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(35, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(35)
            }
        }
    } catch (e: Exception) {
        // Safe fallback
    }
}

@Composable
fun Keypad(
    onDigitClick: (String) -> Unit,
    onOperatorClick: (String) -> Unit,
    onDecimalClick: () -> Unit,
    onPercentageClick: () -> Unit,
    onToggleSignClick: () -> Unit,
    onClearClick: () -> Unit,
    onEqualClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onFunctionClick: (String) -> Unit,
    onMemoryClear: () -> Unit,
    onMemoryRecall: () -> Unit,
    onMemoryStore: () -> Unit,
    onMemoryAdd: () -> Unit,
    onMemorySubtract: () -> Unit,
    onAngleToggle: () -> Unit,
    angleMode: com.ritikthakur.rtcalc.data.repository.AngleMode,
    hapticEnabled: Boolean,
    isDark: Boolean,
    isScientific: Boolean
) {
    val numberBg = if (isDark) DarkButtonNumber else LightButtonNumber
    val numberText = MaterialTheme.colorScheme.onBackground
    val utilityBg = if (isDark) DarkButtonUtility else LightButtonUtility
    val utilityText = Color.Black
    val operationBg = Orange
    val operationText = Color.White
    val sciBg = if (isDark) Color(0xFF2C2C2E) else Color(0xFFD1D1D6)
    val sciText = MaterialTheme.colorScheme.onBackground

    if (isScientific) {
        // Landscape or Tablet Scientific Layout: 7 columns of scientific on left, 4 columns standard on right
        val angleLabel = if (angleMode == com.ritikthakur.rtcalc.data.repository.AngleMode.DEGREE) "DEG" else "RAD"

        val sciButtons = listOf(
            listOf(
                CalcButtonData("MC", sciBg, sciText) { onMemoryClear() },
                CalcButtonData("MR", sciBg, sciText) { onMemoryRecall() },
                CalcButtonData("MS", sciBg, sciText) { onMemoryStore() },
                CalcButtonData("M+", sciBg, sciText) { onMemoryAdd() },
                CalcButtonData("M-", sciBg, sciText) { onMemorySubtract() },
                CalcButtonData("(", sciBg, sciText) { onFunctionClick("(") },
                CalcButtonData(")", sciBg, sciText) { onFunctionClick(")") }
            ),
            listOf(
                CalcButtonData("sin", sciBg, sciText) { onFunctionClick("sin") },
                CalcButtonData("cos", sciBg, sciText) { onFunctionClick("cos") },
                CalcButtonData("tan", sciBg, sciText) { onFunctionClick("tan") },
                CalcButtonData("asin", sciBg, sciText) { onFunctionClick("asin") },
                CalcButtonData("acos", sciBg, sciText) { onFunctionClick("acos") },
                CalcButtonData("atan", sciBg, sciText) { onFunctionClick("atan") },
                CalcButtonData(angleLabel, sciBg, sciText) { onAngleToggle() }
            ),
            listOf(
                CalcButtonData("sinh", sciBg, sciText) { onFunctionClick("sinh") },
                CalcButtonData("cosh", sciBg, sciText) { onFunctionClick("cosh") },
                CalcButtonData("tanh", sciBg, sciText) { onFunctionClick("tanh") },
                CalcButtonData("π", sciBg, sciText) { onFunctionClick("pi") },
                CalcButtonData("e", sciBg, sciText) { onFunctionClick("e") },
                CalcButtonData("Rand", sciBg, sciText) { onFunctionClick("Rand") },
                CalcButtonData("mod", sciBg, sciText) { onFunctionClick("mod") }
            ),
            listOf(
                CalcButtonData("x²", sciBg, sciText) { onFunctionClick("^2") },
                CalcButtonData("x³", sciBg, sciText) { onFunctionClick("^3") },
                CalcButtonData("xʸ", sciBg, sciText) { onFunctionClick("^") },
                CalcButtonData("1/x", sciBg, sciText) { onFunctionClick("1/x") },
                CalcButtonData("√", sciBg, sciText) { onFunctionClick("sqrt") },
                CalcButtonData("n!", sciBg, sciText) { onFunctionClick("!") },
                CalcButtonData("nPr", sciBg, sciText) { onFunctionClick("nPr") }
            ),
            listOf(
                CalcButtonData("log", sciBg, sciText) { onFunctionClick("log") },
                CalcButtonData("ln", sciBg, sciText) { onFunctionClick("ln") },
                CalcButtonData("abs", sciBg, sciText) { onFunctionClick("abs") },
                CalcButtonData("nCr", sciBg, sciText) { onFunctionClick("nCr") },
                CalcButtonData("floor", sciBg, sciText) { onFunctionClick("floor") },
                CalcButtonData("ceil", sciBg, sciText) { onFunctionClick("ceil") },
                CalcButtonData("φ", sciBg, sciText) { onFunctionClick("phi") }
            )
        )

        val stdButtons = listOf(
            listOf(
                CalcButtonData("AC", utilityBg, utilityText) { onClearClick() },
                CalcButtonData("⌫", utilityBg, utilityText) { onDeleteClick() },
                CalcButtonData("%", utilityBg, utilityText) { onPercentageClick() },
                CalcButtonData("÷", operationBg, operationText) { onOperatorClick("÷") }
            ),
            listOf(
                CalcButtonData("7", numberBg, numberText) { onDigitClick("7") },
                CalcButtonData("8", numberBg, numberText) { onDigitClick("8") },
                CalcButtonData("9", numberBg, numberText) { onDigitClick("9") },
                CalcButtonData("×", operationBg, operationText) { onOperatorClick("×") }
            ),
            listOf(
                CalcButtonData("4", numberBg, numberText) { onDigitClick("4") },
                CalcButtonData("5", numberBg, numberText) { onDigitClick("5") },
                CalcButtonData("6", numberBg, numberText) { onDigitClick("6") },
                CalcButtonData("-", operationBg, operationText) { onOperatorClick("-") }
            ),
            listOf(
                CalcButtonData("1", numberBg, numberText) { onDigitClick("1") },
                CalcButtonData("2", numberBg, numberText) { onDigitClick("2") },
                CalcButtonData("3", numberBg, numberText) { onDigitClick("3") },
                CalcButtonData("+", operationBg, operationText) { onOperatorClick("+") }
            ),
            listOf(
                CalcButtonData("±", numberBg, numberText) { onToggleSignClick() },
                CalcButtonData("0", numberBg, numberText) { onDigitClick("0") },
                CalcButtonData(".", numberBg, numberText) { onDecimalClick() },
                CalcButtonData("=", operationBg, operationText) { onEqualClick() }
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Left Side Scientific portion
            Column(
                modifier = Modifier.weight(1.75f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                sciButtons.forEach { row ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        row.forEach { btn ->
                            CalcButton(
                                data = btn,
                                hapticEnabled = hapticEnabled,
                                isSciBtn = true,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            // Right Side Standard portion
            Column(
                modifier = Modifier.weight(1.0f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                stdButtons.forEach { row ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        row.forEach { btn ->
                            CalcButton(
                                data = btn,
                                hapticEnabled = hapticEnabled,
                                isSciBtn = false,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    } else {
        // Portrait Mobile Standard Grid
        val buttonRows = listOf(
            listOf(
                CalcButtonData("AC", utilityBg, utilityText) { onClearClick() },
                CalcButtonData("⌫", utilityBg, utilityText) { onDeleteClick() },
                CalcButtonData("%", utilityBg, utilityText) { onPercentageClick() },
                CalcButtonData("÷", operationBg, operationText) { onOperatorClick("÷") }
            ),
            listOf(
                CalcButtonData("7", numberBg, numberText) { onDigitClick("7") },
                CalcButtonData("8", numberBg, numberText) { onDigitClick("8") },
                CalcButtonData("9", numberBg, numberText) { onDigitClick("9") },
                CalcButtonData("×", operationBg, operationText) { onOperatorClick("×") }
            ),
            listOf(
                CalcButtonData("4", numberBg, numberText) { onDigitClick("4") },
                CalcButtonData("5", numberBg, numberText) { onDigitClick("5") },
                CalcButtonData("6", numberBg, numberText) { onDigitClick("6") },
                CalcButtonData("-", operationBg, operationText) { onOperatorClick("-") }
            ),
            listOf(
                CalcButtonData("1", numberBg, numberText) { onDigitClick("1") },
                CalcButtonData("2", numberBg, numberText) { onDigitClick("2") },
                CalcButtonData("3", numberBg, numberText) { onDigitClick("3") },
                CalcButtonData("+", operationBg, operationText) { onOperatorClick("+") }
            ),
            listOf(
                CalcButtonData("±", numberBg, numberText) { onToggleSignClick() },
                CalcButtonData("0", numberBg, numberText) { onDigitClick("0") },
                CalcButtonData(".", numberBg, numberText) { onDecimalClick() },
                CalcButtonData("=", operationBg, operationText) { onEqualClick() }
            )
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            buttonRows.forEach { rowButtons ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    rowButtons.forEach { btn ->
                        CalcButton(
                            data = btn,
                            hapticEnabled = hapticEnabled,
                            isSciBtn = false,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ScientificPortraitPanel(
    onFunctionClick: (String) -> Unit,
    onMemoryClear: () -> Unit,
    onMemoryRecall: () -> Unit,
    onMemoryStore: () -> Unit,
    onMemoryAdd: () -> Unit,
    onMemorySubtract: () -> Unit,
    onAngleToggle: () -> Unit,
    angleMode: com.ritikthakur.rtcalc.data.repository.AngleMode,
    hapticEnabled: Boolean,
    isDark: Boolean
) {
    var is2nd by remember { mutableStateOf(false) }
    var isHyp by remember { mutableStateOf(false) }

    val sciBg = if (isDark) Color(0xFF2C2C2E) else Color(0xFFD1D1D6)
    val sciText = MaterialTheme.colorScheme.onBackground
    val shiftBg = Orange
    val shiftText = Color.White

    val row1 = listOf(
        CalcButtonData("2nd", if (is2nd) shiftBg else sciBg, if (is2nd) shiftText else sciText) { is2nd = !is2nd },
        CalcButtonData("hyp", if (isHyp) shiftBg else sciBg, if (isHyp) shiftText else sciText) { isHyp = !isHyp },
        CalcButtonData(if (is2nd) "asin" else if (isHyp) "sinh" else "sin", sciBg, sciText) {
            onFunctionClick(if (is2nd) "asin" else if (isHyp) "sinh" else "sin")
            is2nd = false
            isHyp = false
        },
        CalcButtonData(if (is2nd) "acos" else if (isHyp) "cosh" else "cos", sciBg, sciText) {
            onFunctionClick(if (is2nd) "acos" else if (isHyp) "cosh" else "cos")
            is2nd = false
            isHyp = false
        },
        CalcButtonData(if (is2nd) "atan" else if (isHyp) "tanh" else "tan", sciBg, sciText) {
            onFunctionClick(if (is2nd) "atan" else if (isHyp) "tanh" else "tan")
            is2nd = false
            isHyp = false
        }
    )

    val row2 = listOf(
        CalcButtonData(if (is2nd) "1/x" else "log", sciBg, sciText) {
            onFunctionClick(if (is2nd) "1/x" else "log")
            is2nd = false
        },
        CalcButtonData(if (is2nd) "mod" else "ln", sciBg, sciText) {
            onFunctionClick(if (is2nd) "mod" else "ln")
            is2nd = false
        },
        CalcButtonData(if (is2nd) "nPr" else "√", sciBg, sciText) {
            onFunctionClick(if (is2nd) "nPr" else "sqrt")
            is2nd = false
        },
        CalcButtonData(if (is2nd) "x³" else "x²", sciBg, sciText) {
            onFunctionClick(if (is2nd) "^3" else "^2")
            is2nd = false
        },
        CalcButtonData(if (is2nd) "nCr" else "xʸ", sciBg, sciText) {
            onFunctionClick(if (is2nd) "nCr" else "^")
            is2nd = false
        }
    )

    val row3 = listOf(
        CalcButtonData(if (is2nd) "Rand" else "π", sciBg, sciText) {
            onFunctionClick(if (is2nd) "Rand" else "pi")
            is2nd = false
        },
        CalcButtonData(if (is2nd) "D/R" else "e", sciBg, sciText) {
            if (is2nd) onAngleToggle() else onFunctionClick("e")
            is2nd = false
        },
        CalcButtonData("n!", sciBg, sciText) { onFunctionClick("!") },
        CalcButtonData("(", sciBg, sciText) { onFunctionClick("(") },
        CalcButtonData(")", sciBg, sciText) { onFunctionClick(")") }
    )

    val row4 = listOf(
        CalcButtonData("MC", sciBg, sciText) { onMemoryClear() },
        CalcButtonData("MR", sciBg, sciText) { onMemoryRecall() },
        CalcButtonData("MS", sciBg, sciText) { onMemoryStore() },
        CalcButtonData("M+", sciBg, sciText) { onMemoryAdd() },
        CalcButtonData("M-", sciBg, sciText) { onMemorySubtract() }
    )

    val rows = listOf(row1, row2, row3, row4)

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        rows.forEach { rowButtons ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowButtons.forEach { btn ->
                    CalcButton(
                        data = btn,
                        hapticEnabled = hapticEnabled,
                        isSciBtn = true,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

data class CalcButtonData(
    val text: String,
    val backgroundColor: Color,
    val textColor: Color,
    val onClick: () -> Unit
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalcButton(
    data: CalcButtonData,
    hapticEnabled: Boolean,
    isSciBtn: Boolean,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val view = LocalView.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // Smooth press scaling animation
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.92f else 1.0f,
        label = "ButtonPressScale"
    )

    val finalBgColor = if (isPressed) {
        if (data.backgroundColor == Orange) OrangePressed else data.backgroundColor.copy(alpha = 0.7f)
    } else {
        data.backgroundColor
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .aspectRatio(if (isSciBtn) 1.25f else 1f) // aspect ratio optimized for standard vs scientific
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clip(RoundedCornerShape(20.dp))
            .background(finalBgColor)
            .combinedClickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    if (hapticEnabled) {
                        triggerVibration(context)
                    }
                    data.onClick()
                }
            )
            .padding(4.dp)
    ) {
        Text(
            text = data.text,
            color = data.textColor,
            fontSize = if (isSciBtn) 15.sp else 23.sp,
            style = MaterialTheme.typography.titleLarge
        )
    }
}
