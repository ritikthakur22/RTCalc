package com.ritikthakur.rtcalc.ui.view

import android.view.HapticFeedbackConstants
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ritikthakur.rtcalc.ui.theme.*

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
        // Landscape or Tablet Scientific Layout: Left side has scientific, right side has standard
        var isShifted by remember { mutableStateOf(false) }

        val sciButtons = listOf(
            listOf(
                CalcButtonData(if (isShifted) "2nd" else "2nd", if (isShifted) Orange else sciBg, if (isShifted) Color.White else sciText) { isShifted = !isShifted },
                CalcButtonData(if (isShifted) "asin" else "sin", sciBg, sciText) { onFunctionClick(if (isShifted) "asin" else "sin") },
                CalcButtonData("sinh", sciBg, sciText) { onFunctionClick("sinh") },
                CalcButtonData(if (isShifted) "x³" else "x²", sciBg, sciText) { onFunctionClick(if (isShifted) "^3" else "^2") },
                CalcButtonData(if (isShifted) "ln" else "log", sciBg, sciText) { onFunctionClick(if (isShifted) "ln" else "log") },
                CalcButtonData("MC", sciBg, sciText) { onMemoryClear() }
            ),
            listOf(
                CalcButtonData("π", sciBg, sciText) { onFunctionClick("pi") },
                CalcButtonData(if (isShifted) "acos" else "cos", sciBg, sciText) { onFunctionClick(if (isShifted) "acos" else "cos") },
                CalcButtonData("cosh", sciBg, sciText) { onFunctionClick("cosh") },
                CalcButtonData("x^y", sciBg, sciText) { onFunctionClick("^") },
                CalcButtonData("log₂", sciBg, sciText) { onFunctionClick("log2") },
                CalcButtonData("MR", sciBg, sciText) { onMemoryRecall() }
            ),
            listOf(
                CalcButtonData("e", sciBg, sciText) { onFunctionClick("e") },
                CalcButtonData(if (isShifted) "atan" else "tan", sciBg, sciText) { onFunctionClick(if (isShifted) "atan" else "tan") },
                CalcButtonData("tanh", sciBg, sciText) { onFunctionClick("tanh") },
                CalcButtonData(if (isShifted) "e^x" else "10^x", sciBg, sciText) { onFunctionClick(if (isShifted) "e^" else "10^") },
                CalcButtonData(if (isShifted) "nPr" else "nCr", sciBg, sciText) { onFunctionClick(if (isShifted) "nPr" else "nCr") },
                CalcButtonData("MS", sciBg, sciText) { onMemoryStore() }
            ),
            listOf(
                CalcButtonData("φ", sciBg, sciText) { onFunctionClick("phi") },
                CalcButtonData("abs", sciBg, sciText) { onFunctionClick("abs") },
                CalcButtonData("x!", sciBg, sciText) { onFunctionClick("!") },
                CalcButtonData("√", sciBg, sciText) { onFunctionClick("sqrt") },
                CalcButtonData(if (isShifted) "lcm" else "gcd", sciBg, sciText) { onFunctionClick(if (isShifted) "lcm" else "gcd") },
                CalcButtonData("M+", sciBg, sciText) { onMemoryAdd() }
            ),
            listOf(
                CalcButtonData("(", sciBg, sciText) { onFunctionClick("(") },
                CalcButtonData(")", sciBg, sciText) { onFunctionClick(")") },
                CalcButtonData(",", sciBg, sciText) { onFunctionClick(",") },
                CalcButtonData("rand", sciBg, sciText) { onFunctionClick("random()") },
                CalcButtonData(if (isShifted) "ceil" else "floor", sciBg, sciText) { onFunctionClick(if (isShifted) "ceil" else "floor") },
                CalcButtonData("M-", sciBg, sciText) { onMemorySubtract() }
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
                modifier = Modifier.weight(1.5f),
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
            .aspectRatio(if (isSciBtn) 1.2f else 1f) // slightly wider for scientific buttons
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
                        view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                    }
                    data.onClick()
                }
            )
            .padding(4.dp)
    ) {
        Text(
            text = data.text,
            color = data.textColor,
            fontSize = if (isSciBtn) 16.sp else 24.sp,
            style = MaterialTheme.typography.titleLarge
        )
    }
}
