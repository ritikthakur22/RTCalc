package com.ritikthakur.rtcalc

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ritikthakur.rtcalc.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CalculatorUiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testCalculatorBasicUiVerification() {
        // Assert that the app title exists on screen
        composeTestRule.onNodeWithText("RTCalc").assertExists()
        
        // Assert AC button exists
        composeTestRule.onNodeWithText("AC").assertExists()
        
        // Verify key digits exist
        composeTestRule.onNodeWithText("7").assertExists()
        composeTestRule.onNodeWithText("5").assertExists()
        composeTestRule.onNodeWithText("=").assertExists()
    }
}
