package com.ritikthakur.rtcalc.domain

import com.ritikthakur.rtcalc.data.repository.AngleMode
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
        assertEquals("0.3", result)
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

    @Test
    fun testTrigonometryDegrees() {
        val result = ExpressionEvaluator.evaluate("sin(30)", angleMode = AngleMode.DEGREE)
        assertEquals("0.5", result)
    }

    @Test
    fun testTrigonometryRadians() {
        val result = ExpressionEvaluator.evaluate("sin(pi / 6)", angleMode = AngleMode.RADIAN)
        assertEquals("0.5", result)
    }

    @Test
    fun testInverseTrigDegrees() {
        val result = ExpressionEvaluator.evaluate("asin(0.5)", angleMode = AngleMode.DEGREE)
        assertEquals("30", result)
    }

    @Test
    fun testLogarithms() {
        val log10Result = ExpressionEvaluator.evaluate("log(100)")
        assertEquals("2", log10Result)

        val lnResult = ExpressionEvaluator.evaluate("ln(e)")
        assertEquals("1", lnResult)

        val log2Result = ExpressionEvaluator.evaluate("log2(8)")
        assertEquals("3", log2Result)
    }

    @Test
    fun testCombinationsAndPermutations() {
        val nCrResult = ExpressionEvaluator.evaluate("nCr(5, 2)")
        assertEquals("10", nCrResult)

        val nPrResult = ExpressionEvaluator.evaluate("nPr(5, 2)")
        assertEquals("20", nPrResult)
    }

    @Test
    fun testGcdAndLcm() {
        val gcdResult = ExpressionEvaluator.evaluate("gcd(12, 18)")
        assertEquals("6", gcdResult)

        val lcmResult = ExpressionEvaluator.evaluate("lcm(12, 18)")
        assertEquals("36", lcmResult)
    }

    @Test
    fun testFactorial() {
        val result = ExpressionEvaluator.evaluate("5!")
        assertEquals("120", result)
    }

    @Test
    fun testConstants() {
        val piResult = ExpressionEvaluator.evaluate("pi")
        assertEquals("3.1415926536", piResult)

        val phiResult = ExpressionEvaluator.evaluate("phi")
        assertEquals("1.6180339887", phiResult)
    }
}
