package com.ritikthakur.rtcalc.domain

import org.junit.Assert.assertEquals
import org.junit.Test

class ExpressionEvaluatorTest {

    @Test
    fun testBasicAddition() {
        val result = ExpressionEvaluator.evaluate("15 + 27")
        assertEquals("42", result)
    }

    @Test
    fun testBasicSubtraction() {
        val result = ExpressionEvaluator.evaluate("100 - 45.5")
        assertEquals("54.5", result)
    }

    @Test
    fun testBasicMultiplication() {
        val result = ExpressionEvaluator.evaluate("6 × 7")
        assertEquals("42", result)
    }

    @Test
    fun testBasicDivision() {
        val result = ExpressionEvaluator.evaluate("81 ÷ 9")
        assertEquals("9", result)
    }

    @Test
    fun testOperatorPrecedence() {
        val result = ExpressionEvaluator.evaluate("2 + 3 × 4")
        assertEquals("14", result)
    }

    @Test
    fun testDecimalOperations() {
        val result = ExpressionEvaluator.evaluate("0.1 + 0.2")
        assertEquals("0.3", result) // Testing that BigDecimal prevents floating point errors
    }

    @Test
    fun testNegativeNumbers() {
        val result = ExpressionEvaluator.evaluate("-5 + 12")
        assertEquals("7", result)
    }

    @Test
    fun testPercentage() {
        val result = ExpressionEvaluator.evaluate("25%")
        assertEquals("0.25", result)
    }

    @Test
    fun testPercentageMultiplication() {
        val result = ExpressionEvaluator.evaluate("50% × 200")
        assertEquals("100", result)
    }

    @Test
    fun testDivisionByZero() {
        val result = ExpressionEvaluator.evaluate("10 ÷ 0")
        assertEquals("Error: Division by zero", result)
    }

    @Test
    fun testTrailingOperatorStripping() {
        val result = ExpressionEvaluator.evaluate("12 + 8 ×")
        assertEquals("20", result)
    }

    @Test
    fun testLargeNumberScaling() {
        val result = ExpressionEvaluator.evaluate("999999999999999 × 9999")
        assertEquals("9.99900000e+18", result)
    }
}
