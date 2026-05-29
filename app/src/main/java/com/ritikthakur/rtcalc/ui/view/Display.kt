package com.ritikthakur.rtcalc.ui.view

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ritikthakur.rtcalc.ui.theme.LightTextSecondary

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Display(
    expression: String,
    displayValue: String,
    modifier: Modifier = Modifier
) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    // Dynamically scale down font size as display text gets longer to prevent wrap-around
    val displayFontSize = when {
        displayValue.length > 14 -> 28.sp
        displayValue.length > 10 -> 40.sp
        displayValue.length > 7 -> 52.sp
        else -> 68.sp
    }

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
        // Equation / Expression History Line
        Text(
            text = expression,
            fontSize = 22.sp,
            color = LightTextSecondary,
            textAlign = TextAlign.End,
            maxLines = 2,
            modifier = Modifier.fillMaxWidth()
        )

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
