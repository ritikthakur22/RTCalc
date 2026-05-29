package com.ritikthakur.rtcalc.domain;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\f\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001\u0014B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J,\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000bJ \u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0010\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0010\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0004H\u0002\u00a8\u0006\u0015"}, d2 = {"Lcom/ritikthakur/rtcalc/domain/ExpressionEvaluator;", "", "()V", "evaluate", "", "expression", "angleMode", "Lcom/ritikthakur/rtcalc/data/repository/AngleMode;", "precision", "", "useScientific", "", "formatResult", "value", "", "isTrailingOperator", "c", "", "sanitizeExpression", "expr", "Parser", "app_debug"})
public final class ExpressionEvaluator {
    @org.jetbrains.annotations.NotNull
    public static final com.ritikthakur.rtcalc.domain.ExpressionEvaluator INSTANCE = null;
    
    private ExpressionEvaluator() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String evaluate(@org.jetbrains.annotations.NotNull
    java.lang.String expression, @org.jetbrains.annotations.NotNull
    com.ritikthakur.rtcalc.data.repository.AngleMode angleMode, int precision, boolean useScientific) {
        return null;
    }
    
    private final boolean isTrailingOperator(char c) {
        return false;
    }
    
    private final java.lang.String sanitizeExpression(java.lang.String expr) {
        return null;
    }
    
    private final java.lang.String formatResult(double value, int precision, boolean useScientific) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\f\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\n\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001aH\u0002J\u000e\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\nJ\u0006\u0010\u001f\u001a\u00020\u001dJ\u001e\u0010 \u001a\u00020\u00182\u0006\u0010!\u001a\u00020\u00032\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00180#H\u0002J\u0010\u0010$\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0018H\u0002J\u0010\u0010%\u001a\u00020\u00182\u0006\u0010&\u001a\u00020\u0018H\u0002J\u0018\u0010\'\u001a\u00020\u001a2\u0006\u0010(\u001a\u00020\u001a2\u0006\u0010)\u001a\u00020\u001aH\u0002J\u0018\u0010*\u001a\u00020\u001a2\u0006\u0010(\u001a\u00020\u001a2\u0006\u0010)\u001a\u00020\u001aH\u0002J\u0006\u0010+\u001a\u00020,J\u0006\u0010-\u001a\u00020\u0018J\u0006\u0010.\u001a\u00020\u0018J\u0006\u0010/\u001a\u00020\u0018J\u0006\u00100\u001a\u00020\u0018J\u0006\u00101\u001a\u00020\u0018J\u0006\u00102\u001a\u00020\u0018J\u0018\u00103\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001aH\u0002J\u0010\u00104\u001a\u00020\u00182\u0006\u00105\u001a\u00020\u0018H\u0002R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0012X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016\u00a8\u00066"}, d2 = {"Lcom/ritikthakur/rtcalc/domain/ExpressionEvaluator$Parser;", "", "input", "", "angleMode", "Lcom/ritikthakur/rtcalc/data/repository/AngleMode;", "(Ljava/lang/String;Lcom/ritikthakur/rtcalc/data/repository/AngleMode;)V", "getAngleMode", "()Lcom/ritikthakur/rtcalc/data/repository/AngleMode;", "ch", "", "getCh", "()C", "setCh", "(C)V", "getInput", "()Ljava/lang/String;", "pos", "", "getPos", "()I", "setPos", "(I)V", "combinations", "", "n", "", "r", "eat", "", "charToEat", "eatModOperator", "evaluateFunction", "name", "args", "", "factorial", "fromRadians", "rad", "gcd", "a", "b", "lcm", "nextChar", "", "parse", "parseExpression", "parseFactor", "parsePrimary", "parseTerm", "parseUnary", "permutations", "toRadians", "angle", "app_debug"})
    static final class Parser {
        @org.jetbrains.annotations.NotNull
        private final java.lang.String input = null;
        @org.jetbrains.annotations.NotNull
        private final com.ritikthakur.rtcalc.data.repository.AngleMode angleMode = null;
        private int pos = -1;
        private char ch = ' ';
        
        public Parser(@org.jetbrains.annotations.NotNull
        java.lang.String input, @org.jetbrains.annotations.NotNull
        com.ritikthakur.rtcalc.data.repository.AngleMode angleMode) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getInput() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.ritikthakur.rtcalc.data.repository.AngleMode getAngleMode() {
            return null;
        }
        
        public final int getPos() {
            return 0;
        }
        
        public final void setPos(int p0) {
        }
        
        public final char getCh() {
            return '\u0000';
        }
        
        public final void setCh(char p0) {
        }
        
        public final void nextChar() {
        }
        
        public final boolean eat(char charToEat) {
            return false;
        }
        
        public final boolean eatModOperator() {
            return false;
        }
        
        public final double parse() {
            return 0.0;
        }
        
        public final double parseExpression() {
            return 0.0;
        }
        
        public final double parseTerm() {
            return 0.0;
        }
        
        public final double parseFactor() {
            return 0.0;
        }
        
        public final double parseUnary() {
            return 0.0;
        }
        
        public final double parsePrimary() {
            return 0.0;
        }
        
        private final double evaluateFunction(java.lang.String name, java.util.List<java.lang.Double> args) {
            return 0.0;
        }
        
        private final double toRadians(double angle) {
            return 0.0;
        }
        
        private final double fromRadians(double rad) {
            return 0.0;
        }
        
        private final double factorial(double n) {
            return 0.0;
        }
        
        private final double combinations(long n, long r) {
            return 0.0;
        }
        
        private final double permutations(long n, long r) {
            return 0.0;
        }
        
        private final long gcd(long a, long b) {
            return 0L;
        }
        
        private final long lcm(long a, long b) {
            return 0L;
        }
    }
}