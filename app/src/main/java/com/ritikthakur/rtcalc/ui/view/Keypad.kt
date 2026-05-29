package com.ritikthakur.rtcalc.ui.view

import android.view.HapticFeedbackConstants
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ritikthakur.rtcalc.ui.theme.DarkButtonNumber
import com.ritikthakur.rtcalc.ui.theme.DarkButtonUtility
import com.ritikthakur.rtcalc.ui.theme.LightButtonNumber
import com.ritikthakur.rtcalc.ui.theme.LightButtonUtility
import com.ritikthakur.rtcalc.ui.theme.Orange
import com.ritikthakur.rtcalc.ui.theme.OrangePressed

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
    hapticEnabled: Boolean,
    isDark: Boolean
) {
    val numberBg = if (isDark) DarkButtonNumber else LightButtonNumber
    val numberText = MaterialTheme.colorScheme.onBackground
    val utilityBg = if (isDark) DarkButtonUtility else LightButtonUtility
    val utilityText = if (isDark) Color.Black else Color.Black
    val operationBg = Orange
    val operationText = Color.White

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
    modifier: Modifier = Modifier
) {
    val view = LocalView.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // Smooth press scaling animation
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.90f else 1.0f,
        label = "ButtonPressScale"
    )

    // Dynamic background color change on press
    val finalBgColor = if (isPressed) {
        if (data.backgroundColor == Orange) OrangePressed else data.backgroundColor.copy(alpha = 0.7f)
    } else {
        data.backgroundColor
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .aspectRatio(1f)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clip(CircleShape)
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
            .padding(8.dp)
    ) {
        Text(
            text = data.text,
            color = data.textColor,
            fontSize = 28.sp,
            style = MaterialTheme.typography.titleLarge
        )
    }
}
