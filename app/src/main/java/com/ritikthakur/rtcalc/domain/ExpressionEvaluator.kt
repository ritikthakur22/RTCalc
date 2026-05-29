package com.ritikthakur.rtcalc.domain

import com.ritikthakur.rtcalc.data.repository.AngleMode
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.Locale
import kotlin.math.*

object ExpressionEvaluator {

    fun evaluate(
        expression: String,
        angleMode: AngleMode = AngleMode.DEGREE,
        precision: Int = 10,
        useScientific: Boolean = false
    ): String {
        if (expression.isBlank()) return "0"
        
        try {
            var sanitized = expression.trim()
            // Strip trailing binary operators for convenience
            while (sanitized.isNotEmpty() && isTrailingOperator(sanitized.last())) {
                sanitized = sanitized.substring(0, sanitized.length - 1).trim()
            }

            val readyExpr = sanitizeExpression(sanitized)
            if (readyExpr.isBlank()) return "0"

            val parser = Parser(readyExpr, angleMode)
            val result = parser.parse()
            
            if (result.isNaN()) return "Error: Undefined"
            if (result.isInfinite()) return "Error: Infinite"
            
            return formatResult(result, precision, useScientific)
        } catch (e: ArithmeticException) {
            return "Error: " + (e.message ?: "Math Error")
        } catch (e: Exception) {
            return "Error"
        }
    }

    private fun isTrailingOperator(c: Char): Boolean {
        return c == '+' || c == '-' || c == '×' || c == '÷' || c == '*' || c == '/' || c == '^' || c == ','
    }

    private fun sanitizeExpression(expr: String): String {
        return expr
            .replace("×", "*")
            .replace("÷", "/")
            .replace("π", "pi")
            .replace("e", "e")
            .replace("φ", "phi")
            .trim()
    }

    private fun formatResult(value: Double, precision: Int, useScientific: Boolean): String {
        val rawBigDecimal = try {
            BigDecimal(value.toString())
        } catch (e: Exception) {
            BigDecimal(value)
        }

        val rounded = rawBigDecimal.setScale(precision, RoundingMode.HALF_UP).stripTrailingZeros()
        
        if (useScientific || rounded.abs() >= BigDecimal("1e12") || (rounded.compareTo(BigDecimal.ZERO) != 0 && rounded.abs() < BigDecimal("1e-6"))) {
            return String.format(Locale.US, "%.${precision - 2}e", value)
        }

        val plainStr = rounded.toPlainString()
        if (plainStr.endsWith(".0")) {
            return plainStr.substring(0, plainStr.length - 2)
        }
        return plainStr
    }

    private class Parser(val input: String, val angleMode: AngleMode) {
        var pos = -1
        var ch = ' '

        fun nextChar() {
            pos++
            ch = if (pos < input.length) input[pos] else '\u0000'
        }

        fun eat(charToEat: Char): Boolean {
            while (ch == ' ') nextChar()
            if (ch == charToEat) {
                nextChar()
                return true
            }
            return false
        }

        fun eatModOperator(): Boolean {
            while (ch == ' ') nextChar()
            if (ch == 'm' && pos + 2 < input.length && input[pos+1] == 'o' && input[pos+2] == 'd') {
                nextChar() // m
                nextChar() // o
                nextChar() // d
                return true
            }
            return false
        }

        fun parse(): Double {
            nextChar()
            val x = parseExpression()
            if (pos < input.length) throw IllegalArgumentException("Unexpected: $ch")
            return x
        }

        // Expression = Term ( [+-] Term )*
        fun parseExpression(): Double {
            var x = parseTerm()
            while (true) {
                if (eat('+')) x += parseTerm()
                else if (eat('-')) x -= parseTerm()
                else break
            }
            return x
        }

        // Term = Factor ( [*/] Factor | mod Factor )*
        fun parseTerm(): Double {
            var x = parseFactor()
            while (true) {
                if (eat('*')) x *= parseFactor()
                else if (eat('/')) {
                    val divisor = parseFactor()
                    if (divisor == 0.0) throw ArithmeticException("Division by zero")
                    x /= divisor
                } else if (eatModOperator()) {
                    val divisor = parseFactor()
                    if (divisor == 0.0) throw ArithmeticException("Division by zero")
                    x %= divisor
                } else break
            }
            return x
        }

        // Factor = Unary [^] Factor | Unary
        fun parseFactor(): Double {
            var x = parseUnary()
            if (eat('^')) {
                x = x.pow(parseFactor())
            }
            return x
        }

        // Unary = [+-] Unary | Primary [!%]
        fun parseUnary(): Double {
            if (eat('+')) return parseUnary()
            if (eat('-')) return -parseUnary()
            
            var x = parsePrimary()
            
            // Postfix operators
            while (true) {
                if (eat('!')) {
                    x = factorial(x)
                } else if (eat('%')) {
                    x /= 100.0 // percentage
                } else {
                    break
                }
            }
            return x
        }

        // Primary = Number | Constant | Function | ( Expression )
        fun parsePrimary(): Double {
            val startPos = this.pos
            if (eat('(')) {
                val x = parseExpression()
                eat(')')
                return x
            }

            if (ch.isDigit() || ch == '.') {
                while (ch.isDigit() || ch == '.') nextChar()
                return input.substring(startPos, this.pos).toDouble()
            }

            if (ch.isLetter()) {
                while (ch.isLetter() || ch.isDigit()) nextChar()
                val name = input.substring(startPos, this.pos)
                
                if (name == "pi") return Math.PI
                if (name == "e") return Math.E
                if (name == "phi") return 1.618033988749895

                if (eat('(')) {
                    val args = mutableListOf<Double>()
                    if (ch != ')') {
                        args.add(parseExpression())
                        while (eat(',')) {
                            args.add(parseExpression())
                        }
                    }
                    eat(')')

                    return evaluateFunction(name, args)
                } else {
                    val arg = parseUnary()
                    return evaluateFunction(name, listOf(arg))
                }
            }

            throw IllegalArgumentException("Unexpected character: $ch")
        }

        private fun evaluateFunction(name: String, args: List<Double>): Double {
            if (args.isEmpty()) {
                if (name == "random") return Math.random()
                throw IllegalArgumentException("Function $name requires arguments")
            }

            val arg1 = args[0]

            return when (name) {
                "sin" -> sin(toRadians(arg1))
                "cos" -> cos(toRadians(arg1))
                "tan" -> tan(toRadians(arg1))
                "asin" -> fromRadians(asin(arg1))
                "acos" -> fromRadians(acos(arg1))
                "atan" -> fromRadians(atan(arg1))
                "sinh" -> sinh(arg1)
                "cosh" -> cosh(arg1)
                "tanh" -> tanh(arg1)
                "log" -> log10(arg1)
                "ln" -> ln(arg1)
                "log2" -> log2(arg1)
                "sqrt" -> {
                    if (arg1 < 0) throw ArithmeticException("Square root of negative number")
                    sqrt(arg1)
                }
                "abs" -> abs(arg1)
                "floor" -> floor(arg1)
                "ceil" -> ceil(arg1)
                "round" -> round(arg1).toDouble()
                "nCr" -> {
                    if (args.size < 2) throw IllegalArgumentException("nCr requires 2 arguments")
                    combinations(arg1.toLong(), args[1].toLong())
                }
                "nPr" -> {
                    if (args.size < 2) throw IllegalArgumentException("nPr requires 2 arguments")
                    permutations(arg1.toLong(), args[1].toLong())
                }
                "gcd" -> {
                    if (args.size < 2) throw IllegalArgumentException("gcd requires 2 arguments")
                    gcd(arg1.toLong(), args[1].toLong()).toDouble()
                }
                "lcm" -> {
                    if (args.size < 2) throw IllegalArgumentException("lcm requires 2 arguments")
                    lcm(arg1.toLong(), args[1].toLong()).toDouble()
                }
                "mod" -> {
                    if (args.size < 2) throw IllegalArgumentException("mod requires 2 arguments")
                    args[0] % args[1]
                }
                else -> throw IllegalArgumentException("Unknown function: $name")
            }
        }

        private fun toRadians(angle: Double): Double {
            return when (angleMode) {
                AngleMode.DEGREE -> Math.toRadians(angle)
                AngleMode.RADIAN -> angle
                AngleMode.GRADIAN -> angle * (Math.PI / 200.0)
            }
        }

        private fun fromRadians(rad: Double): Double {
            return when (angleMode) {
                AngleMode.DEGREE -> Math.toDegrees(rad)
                AngleMode.RADIAN -> rad
                AngleMode.GRADIAN -> rad * (200.0 / Math.PI)
            }
        }

        private fun factorial(n: Double): Double {
            val num = n.toLong()
            if (num < 0 || num.toDouble() != n) throw ArithmeticException("Factorial undefined for non-integers")
            var result = 1.0
            for (i in 1..num) {
                result *= i
                if (result.isInfinite()) throw ArithmeticException("Factorial overflow")
            }
            return result
        }

        private fun combinations(n: Long, r: Long): Double {
            if (n < 0 || r < 0 || r > n) return 0.0
            var result = 1.0
            for (i in 1..r) {
                result = result * (n - r + i) / i
            }
            return result
        }

        private fun permutations(n: Long, r: Long): Double {
            if (n < 0 || r < 0 || r > n) return 0.0
            var result = 1.0
            for (i in (n - r + 1)..n) {
                result *= i
            }
            return result
        }

        private fun gcd(a: Long, b: Long): Long {
            var x = abs(a)
            var y = abs(b)
            while (y > 0) {
                val temp = y
                y = x % y
                x = temp
            }
            return x
        }

        private fun lcm(a: Long, b: Long): Long {
            if (a == 0L || b == 0L) return 0L
            return abs(a * b) / gcd(a, b)
        }
    }
}
