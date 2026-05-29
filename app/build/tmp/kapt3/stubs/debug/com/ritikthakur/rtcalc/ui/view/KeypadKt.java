package com.ritikthakur.rtcalc.ui.view;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\bH\u0007\u001a\u00f6\u0001\u0010\t\u001a\u00020\u00012\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00010\u000b2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00010\u000b2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00010\u000b2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u001b\u001a\u00020\u00052\u0006\u0010\u001c\u001a\u00020\u0005H\u0007\u00a8\u0006\u001d"}, d2 = {"CalcButton", "", "data", "Lcom/ritikthakur/rtcalc/ui/view/CalcButtonData;", "hapticEnabled", "", "isSciBtn", "modifier", "Landroidx/compose/ui/Modifier;", "Keypad", "onDigitClick", "Lkotlin/Function1;", "", "onOperatorClick", "onDecimalClick", "Lkotlin/Function0;", "onPercentageClick", "onToggleSignClick", "onClearClick", "onEqualClick", "onDeleteClick", "onFunctionClick", "onMemoryClear", "onMemoryRecall", "onMemoryStore", "onMemoryAdd", "onMemorySubtract", "isDark", "isScientific", "app_debug"})
public final class KeypadKt {
    
    @androidx.compose.runtime.Composable
    public static final void Keypad(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onDigitClick, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onOperatorClick, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDecimalClick, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onPercentageClick, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onToggleSignClick, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onClearClick, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onEqualClick, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDeleteClick, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onFunctionClick, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onMemoryClear, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onMemoryRecall, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onMemoryStore, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onMemoryAdd, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onMemorySubtract, boolean hapticEnabled, boolean isDark, boolean isScientific) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.foundation.ExperimentalFoundationApi.class})
    @androidx.compose.runtime.Composable
    public static final void CalcButton(@org.jetbrains.annotations.NotNull
    com.ritikthakur.rtcalc.ui.view.CalcButtonData data, boolean hapticEnabled, boolean isSciBtn, @org.jetbrains.annotations.NotNull
    androidx.compose.ui.Modifier modifier) {
    }
}