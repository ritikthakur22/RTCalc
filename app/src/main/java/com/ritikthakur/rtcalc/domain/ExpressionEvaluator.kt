package com.ritikthakur.rtcalc.domain

import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import java.util.Stack

object ExpressionEvaluator {

    private val MATH_CONTEXT = MathContext(16, RoundingMode.HALF_UP)

    fun evaluate(expression: String): String {
        if (expression.isBlank()) return "0"
        
        try {
            var sanitized = expression.trim()
            // Strip trailing binary operators for convenience (e.g. "5 + 3 +" -> "5 + 3")
            while (sanitized.isNotEmpty() && isTrailingOperator(sanitized.last())) {
                sanitized = sanitized.substring(0, sanitized.length - 1).trim()
            }

            if (sanitized.isBlank()) return "0"

            val tokens = tokenize(sanitized)
            if (tokens.isEmpty()) return "0"
            val rpn = shuntingYard(tokens)
            val result = evaluateRPN(rpn)
            
            return formatResult(result)
        } catch (e: ArithmeticException) {
            return "Error: " + (e.message ?: "Division by zero")
        } catch (e: Exception) {
            return "Error"
        }
    }

    private fun isTrailingOperator(c: Char): Boolean {
        return c == '+' || c == '-' || c == '×' || c == '÷' || c == '*' || c == '/'
    }

    private fun tokenize(expr: String): List<String> {
        val sanitized = expr.replace("×", "*").replace("÷", "/").replace(" ", "")
        val tokens = mutableListOf<String>()
        var i = 0
        val n = sanitized.length
        
        while (i < n) {
            val c = sanitized[i]
            
            if (c.isDigit() || c == '.') {
                val sb = StringBuilder()
                while (i < n && (sanitized[i].isDigit() || sanitized[i] == '.')) {
                    sb.append(sanitized[i])
                    i++
                }
                tokens.add(sb.toString())
            } else if (c in listOf('+', '-', '*', '/', '%')) {
                // Check if minus is unary (negative number)
                // It is unary if it is at the very beginning, OR if it follows another operator (excluding postfix %)
                if (c == '-' && (tokens.isEmpty() || (isOperator(tokens.last()) && tokens.last() != "%"))) {
                    val sb = StringBuilder("-")
                    i++
                    // Parse the number following the negative sign
                    while (i < n && (sanitized[i].isDigit() || sanitized[i] == '.')) {
                        sb.append(sanitized[i])
                        i++
                    }
                    if (sb.length > 1) {
                        tokens.add(sb.toString())
                    } else {
                        tokens.add("-")
                    }
                } else {
                    tokens.add(c.toString())
                    i++
                }
            } else {
                i++
            }
        }
        return tokens
    }

    private fun isOperator(token: String): Boolean {
        return token == "+" || token == "-" || token == "*" || token == "/" || token == "%"
    }

    private fun precedence(op: String): Int {
        return when (op) {
            "+", "-" -> 1
            "*", "/" -> 2
            "%" -> 3
            else -> -1
        }
    }

    private fun shuntingYard(tokens: List<String>): List<String> {
        val output = mutableListOf<String>()
        val stack = Stack<String>()

        for (token in tokens) {
            if (!isOperator(token)) {
                output.add(token)
            } else {
                while (stack.isNotEmpty() && precedence(stack.peek()) >= precedence(token)) {
                    output.add(stack.pop())
                }
                stack.push(token)
            }
        }

        while (stack.isNotEmpty()) {
            output.add(stack.pop())
        }

        return output
    }

    private fun evaluateRPN(rpn: List<String>): BigDecimal {
        val stack = Stack<BigDecimal>()

        for (token in rpn) {
            if (!isOperator(token)) {
                stack.push(BigDecimal(token))
            } else {
                if (token == "%") {
                    if (stack.isEmpty()) throw IllegalArgumentException("Invalid percentage placement")
                    val num = stack.pop()
                    stack.push(num.divide(BigDecimal("100"), MATH_CONTEXT))
                } else {
                    if (stack.size < 2) throw IllegalArgumentException("Invalid expression")
                    val b = stack.pop()
                    val a = stack.pop()
                    val result = when (token) {
                        "+" -> a.add(b, MATH_CONTEXT)
                        "-" -> a.subtract(b, MATH_CONTEXT)
                        "*" -> a.multiply(b, MATH_CONTEXT)
                        "/" -> {
                            if (b.compareTo(BigDecimal.ZERO) == 0) {
                                throw ArithmeticException("Division by zero")
                            }
                            a.divide(b, MATH_CONTEXT)
                        }
                        else -> throw IllegalArgumentException("Unknown operator")
                    }
                    stack.push(result)
                }
            }
        }

        if (stack.size != 1) throw IllegalArgumentException("Invalid expression")
        return stack.pop()
    }

    private fun formatResult(value: BigDecimal): String {
        var result = value.stripTrailingZeros()
        if (result.scale() < 0) {
            result = result.setScale(0)
        }
        
        val scaleStr = result.toPlainString()
        if (scaleStr.length > 15 || (result.compareTo(BigDecimal.ZERO) != 0 && result.abs() < BigDecimal("0.000001"))) {
            // Use scientific notation for very large numbers or extremely small decimals
            return String.format(java.util.Locale.US, "%.8e", result)
        }
        return scaleStr
    }
}
