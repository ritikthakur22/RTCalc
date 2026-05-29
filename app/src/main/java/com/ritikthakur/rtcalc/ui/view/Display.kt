package com.ritikthakur.rtcalc.ui.view

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ritikthakur.rtcalc.ui.theme.LightTextSecondary
import com.ritikthakur.rtcalc.ui.theme.Orange

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Display(
    expressionValue: TextFieldValue,
    onExpressionValueChange: (TextFieldValue) -> Unit,
    displayValue: String,
    modifier: Modifier = Modifier
) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    val isDark = isSystemInDarkTheme()

    val displayFontSize = when {
        displayValue.length > 14 -> 28.sp
        displayValue.length > 10 -> 40.sp
        displayValue.length > 7 -> 52.sp
        else -> 68.sp
    }

    // Custom selection colors (fintech gold-orange accent theme)
    val customTextSelectionColors = TextSelectionColors(
        handleColor = Orange,
        backgroundColor = Orange.copy(alpha = 0.4f)
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                onLongClick = {
                    if (displayValue.isNotEmpty() && displayValue != "0" && !displayValue.startsWith("Error")) {
                        clipboardManager.setText(AnnotatedString(displayValue))
                        Toast.makeText(context, context.getString(com.ritikthakur.rtcalc.R.string.copied_to_clipboard), Toast.LENGTH_SHORT).show()
                    }
                },
                onClick = {}
            )
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Equation / Expression History Line (Editable and selectable via cursor handles)
        CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
            BasicTextField(
                value = expressionValue,
                onValueChange = onExpressionValueChange,
                readOnly = true, // Disable system soft keyboard while maintaining focus, cursor blinking, selection
                textStyle = TextStyle(
                    fontSize = 22.sp,
                    color = if (isDark) Color.White.copy(alpha = 0.7f) else LightTextSecondary,
                    textAlign = TextAlign.End,
                    letterSpacing = 0.5.sp
                ),
                cursorBrush = SolidColor(Orange),
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Active Value / Result Line
        Text(
            text = displayValue,
            fontSize = displayFontSize,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.End,
            maxLines = 1,
            lineHeight = displayFontSize * 1.15f,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
