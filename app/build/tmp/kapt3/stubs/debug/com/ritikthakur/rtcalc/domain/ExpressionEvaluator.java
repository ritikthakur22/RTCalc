package com.ritikthakur.rtcalc.domain;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\f\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006J\u0016\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\u000bH\u0002J\u0010\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\tH\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0006H\u0002J\u0010\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0006H\u0002J\u001c\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00060\u000b2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00060\u000bH\u0002J\u0016\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00060\u000b2\u0006\u0010\u001a\u001a\u00020\u0006H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2 = {"Lcom/ritikthakur/rtcalc/domain/ExpressionEvaluator;", "", "()V", "MATH_CONTEXT", "Ljava/math/MathContext;", "evaluate", "", "expression", "evaluateRPN", "Ljava/math/BigDecimal;", "rpn", "", "formatResult", "value", "isOperator", "", "token", "isTrailingOperator", "c", "", "precedence", "", "op", "shuntingYard", "tokens", "tokenize", "expr", "app_debug"})
public final class ExpressionEvaluator {
    @org.jetbrains.annotations.NotNull
    private static final java.math.MathContext MATH_CONTEXT = null;
    @org.jetbrains.annotations.NotNull
    public static final com.ritikthakur.rtcalc.domain.ExpressionEvaluator INSTANCE = null;
    
    private ExpressionEvaluator() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String evaluate(@org.jetbrains.annotations.NotNull
    java.lang.String expression) {
        return null;
    }
    
    private final boolean isTrailingOperator(char c) {
        return false;
    }
    
    private final java.util.List<java.lang.String> tokenize(java.lang.String expr) {
        return null;
    }
    
    private final boolean isOperator(java.lang.String token) {
        return false;
    }
    
    private final int precedence(java.lang.String op) {
        return 0;
    }
    
    private final java.util.List<java.lang.String> shuntingYard(java.util.List<java.lang.String> tokens) {
        return null;
    }
    
    private final java.math.BigDecimal evaluateRPN(java.util.List<java.lang.String> rpn) {
        return null;
    }
    
    private final java.lang.String formatResult(java.math.BigDecimal value) {
        return null;
    }
}