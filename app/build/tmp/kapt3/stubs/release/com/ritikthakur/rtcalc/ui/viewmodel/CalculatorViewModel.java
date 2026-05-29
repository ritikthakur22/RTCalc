package com.ritikthakur.rtcalc.ui.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u001a\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0006\u0010\u001a\u001a\u00020\u001bJ\u0010\u0010\u001c\u001a\u00020\u000b2\u0006\u0010\u001d\u001a\u00020\u000bH\u0002J\b\u0010\f\u001a\u00020\u000bH\u0002J\b\u0010\u000f\u001a\u00020\u000bH\u0002J\u0010\u0010\u001e\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020\u000bH\u0002J\b\u0010 \u001a\u00020\u0011H\u0002J\u0006\u0010!\u001a\u00020\u001bJ\u0006\u0010\"\u001a\u00020\u001bJ\u0006\u0010#\u001a\u00020\u001bJ\u000e\u0010$\u001a\u00020\u001b2\u0006\u0010%\u001a\u00020\u000bJ\u0006\u0010&\u001a\u00020\u001bJ\u000e\u0010\'\u001a\u00020\u001b2\u0006\u0010(\u001a\u00020\u0015J\u000e\u0010)\u001a\u00020\u001b2\u0006\u0010*\u001a\u00020\u000bJ\u0006\u0010+\u001a\u00020\u001bJ\u0006\u0010,\u001a\u00020\u001bJ\u000e\u0010-\u001a\u00020\u001b2\u0006\u0010.\u001a\u00020\u0011J\u000e\u0010/\u001a\u00020\u001b2\u0006\u00100\u001a\u00020\u0018J\u0010\u00101\u001a\u00020\u000b2\u0006\u0010\u001d\u001a\u00020\u000bH\u0002J\u0010\u00102\u001a\u00020\u001b2\u0006\u00103\u001a\u00020\u000bH\u0002J\u0010\u00104\u001a\u00020\u001b2\u0006\u00103\u001a\u00020\u000bH\u0002R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\rR\u001d\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u00140\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\r\u00a8\u00065"}, d2 = {"Lcom/ritikthakur/rtcalc/ui/viewmodel/CalculatorViewModel;", "Landroidx/lifecycle/ViewModel;", "historyRepository", "Lcom/ritikthakur/rtcalc/data/repository/HistoryRepository;", "settingsRepository", "Lcom/ritikthakur/rtcalc/data/repository/SettingsRepository;", "savedStateHandle", "Landroidx/lifecycle/SavedStateHandle;", "(Lcom/ritikthakur/rtcalc/data/repository/HistoryRepository;Lcom/ritikthakur/rtcalc/data/repository/SettingsRepository;Landroidx/lifecycle/SavedStateHandle;)V", "displayValue", "Lkotlinx/coroutines/flow/StateFlow;", "", "getDisplayValue", "()Lkotlinx/coroutines/flow/StateFlow;", "expression", "getExpression", "hapticEnabled", "", "getHapticEnabled", "historyList", "", "Lcom/ritikthakur/rtcalc/data/local/HistoryEntity;", "getHistoryList", "themeMode", "Lcom/ritikthakur/rtcalc/data/repository/ThemeMode;", "getThemeMode", "clearAllHistory", "", "extractActiveNumber", "expr", "isOperator", "token", "isResultState", "onClearClick", "onDecimalClick", "onDeleteClick", "onDigitClick", "digit", "onEqualClick", "onHistoryItemSelect", "history", "onOperatorClick", "operator", "onPercentageClick", "onToggleSignClick", "setHapticEnabled", "enabled", "setThemeMode", "mode", "toggleLastNumberSign", "updateDisplayValue", "value", "updateExpression", "app_release"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class CalculatorViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.ritikthakur.rtcalc.data.repository.HistoryRepository historyRepository = null;
    @org.jetbrains.annotations.NotNull
    private final com.ritikthakur.rtcalc.data.repository.SettingsRepository settingsRepository = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.SavedStateHandle savedStateHandle = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> expression = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> displayValue = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.ritikthakur.rtcalc.data.local.HistoryEntity>> historyList = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.ritikthakur.rtcalc.data.repository.ThemeMode> themeMode = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> hapticEnabled = null;
    
    @javax.inject.Inject
    public CalculatorViewModel(@org.jetbrains.annotations.NotNull
    com.ritikthakur.rtcalc.data.repository.HistoryRepository historyRepository, @org.jetbrains.annotations.NotNull
    com.ritikthakur.rtcalc.data.repository.SettingsRepository settingsRepository, @org.jetbrains.annotations.NotNull
    androidx.lifecycle.SavedStateHandle savedStateHandle) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getExpression() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getDisplayValue() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.ritikthakur.rtcalc.data.local.HistoryEntity>> getHistoryList() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.ritikthakur.rtcalc.data.repository.ThemeMode> getThemeMode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getHapticEnabled() {
        return null;
    }
    
    private final java.lang.String getExpression() {
        return null;
    }
    
    private final java.lang.String getDisplayValue() {
        return null;
    }
    
    private final boolean isResultState() {
        return false;
    }
    
    public final void onDigitClick(@org.jetbrains.annotations.NotNull
    java.lang.String digit) {
    }
    
    public final void onDecimalClick() {
    }
    
    public final void onOperatorClick(@org.jetbrains.annotations.NotNull
    java.lang.String operator) {
    }
    
    public final void onPercentageClick() {
    }
    
    public final void onToggleSignClick() {
    }
    
    public final void onDeleteClick() {
    }
    
    public final void onClearClick() {
    }
    
    public final void onEqualClick() {
    }
    
    public final void onHistoryItemSelect(@org.jetbrains.annotations.NotNull
    com.ritikthakur.rtcalc.data.local.HistoryEntity history) {
    }
    
    public final void clearAllHistory() {
    }
    
    public final void setThemeMode(@org.jetbrains.annotations.NotNull
    com.ritikthakur.rtcalc.data.repository.ThemeMode mode) {
    }
    
    public final void setHapticEnabled(boolean enabled) {
    }
    
    private final void updateExpression(java.lang.String value) {
    }
    
    private final void updateDisplayValue(java.lang.String value) {
    }
    
    private final boolean isOperator(java.lang.String token) {
        return false;
    }
    
    private final java.lang.String extractActiveNumber(java.lang.String expr) {
        return null;
    }
    
    private final java.lang.String toggleLastNumberSign(java.lang.String expr) {
        return null;
    }
}