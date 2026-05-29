package com.ritikthakur.rtcalc.ui.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b(\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0006\u0010(\u001a\u00020)J\u000e\u0010*\u001a\u00020)2\u0006\u0010+\u001a\u00020\u001bJ\u0010\u0010,\u001a\u00020\u00122\u0006\u0010-\u001a\u00020\u0012H\u0002J\b\u0010\u0013\u001a\u00020\u0012H\u0002J\b\u0010\u0015\u001a\u00020\u0012H\u0002J\u0010\u0010.\u001a\u00020\u00172\u0006\u0010/\u001a\u00020\u0012H\u0002J\b\u00100\u001a\u00020\u0017H\u0002J\u0006\u00101\u001a\u00020)J\u0006\u00102\u001a\u00020)J\u0006\u00103\u001a\u00020)J\u000e\u00104\u001a\u00020)2\u0006\u00105\u001a\u00020\u0012J\u0006\u00106\u001a\u00020)J\u000e\u00107\u001a\u00020)2\u0006\u00108\u001a\u00020\u0012J\u000e\u00109\u001a\u00020)2\u0006\u0010+\u001a\u00020\u001bJ\u0006\u0010:\u001a\u00020)J\u0006\u0010;\u001a\u00020)J\u0006\u0010<\u001a\u00020)J\u0006\u0010=\u001a\u00020)J\u0006\u0010>\u001a\u00020)J\u000e\u0010?\u001a\u00020)2\u0006\u0010@\u001a\u00020\u0012J\u0006\u0010A\u001a\u00020)J\u0006\u0010B\u001a\u00020)J\u000e\u0010C\u001a\u00020)2\u0006\u0010D\u001a\u00020\u000bJ\u000e\u0010E\u001a\u00020)2\u0006\u0010F\u001a\u00020\u000fJ\u000e\u0010G\u001a\u00020)2\u0006\u0010H\u001a\u00020\u0017J\u000e\u0010I\u001a\u00020)2\u0006\u0010H\u001a\u00020\u0017J\u000e\u0010J\u001a\u00020)2\u0006\u0010K\u001a\u00020\u0012J\u000e\u0010L\u001a\u00020)2\u0006\u0010D\u001a\u00020&J\u0010\u0010M\u001a\u00020\u00122\u0006\u0010-\u001a\u00020\u0012H\u0002J\u0010\u0010N\u001a\u00020)2\u0006\u0010O\u001a\u00020\u0012H\u0002J\u0010\u0010P\u001a\u00020)2\u0006\u0010O\u001a\u00020\u0012H\u0002R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\rR\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\rR\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00120\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\rR\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\rR\u001d\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001b0\u001a0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00120\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\rR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00170\n\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\rR\u0017\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00120\"\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010%\u001a\b\u0012\u0004\u0012\u00020&0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\r\u00a8\u0006Q"}, d2 = {"Lcom/ritikthakur/rtcalc/ui/viewmodel/CalculatorViewModel;", "Landroidx/lifecycle/ViewModel;", "historyRepository", "Lcom/ritikthakur/rtcalc/data/repository/HistoryRepository;", "settingsRepository", "Lcom/ritikthakur/rtcalc/data/repository/SettingsRepository;", "savedStateHandle", "Landroidx/lifecycle/SavedStateHandle;", "(Lcom/ritikthakur/rtcalc/data/repository/HistoryRepository;Lcom/ritikthakur/rtcalc/data/repository/SettingsRepository;Landroidx/lifecycle/SavedStateHandle;)V", "angleMode", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/ritikthakur/rtcalc/data/repository/AngleMode;", "getAngleMode", "()Lkotlinx/coroutines/flow/StateFlow;", "decimalPrecision", "", "getDecimalPrecision", "displayValue", "", "getDisplayValue", "expression", "getExpression", "hapticEnabled", "", "getHapticEnabled", "historyList", "", "Lcom/ritikthakur/rtcalc/data/local/HistoryEntity;", "getHistoryList", "memoryValue", "getMemoryValue", "scientificNotation", "getScientificNotation", "searchQuery", "Lkotlinx/coroutines/flow/MutableStateFlow;", "getSearchQuery", "()Lkotlinx/coroutines/flow/MutableStateFlow;", "themeMode", "Lcom/ritikthakur/rtcalc/data/repository/ThemeMode;", "getThemeMode", "clearAllHistory", "", "deleteHistoryItem", "history", "extractActiveNumber", "expr", "isOperator", "token", "isResultState", "onClearClick", "onDecimalClick", "onDeleteClick", "onDigitClick", "digit", "onEqualClick", "onFunctionClick", "functionName", "onHistoryItemSelect", "onMemoryAdd", "onMemoryClear", "onMemoryRecall", "onMemoryStore", "onMemorySubtract", "onOperatorClick", "operator", "onPercentageClick", "onToggleSignClick", "setAngleMode", "mode", "setDecimalPrecision", "precision", "setHapticEnabled", "enabled", "setScientificNotation", "setSearchQuery", "query", "setThemeMode", "toggleLastNumberSign", "updateDisplayValue", "value", "updateExpression", "app_debug"})
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
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> memoryValue = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> searchQuery = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.ritikthakur.rtcalc.data.local.HistoryEntity>> historyList = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.ritikthakur.rtcalc.data.repository.ThemeMode> themeMode = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> hapticEnabled = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.ritikthakur.rtcalc.data.repository.AngleMode> angleMode = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> decimalPrecision = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> scientificNotation = null;
    
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
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getMemoryValue() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> getSearchQuery() {
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
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.ritikthakur.rtcalc.data.repository.AngleMode> getAngleMode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getDecimalPrecision() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getScientificNotation() {
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
    
    public final void onFunctionClick(@org.jetbrains.annotations.NotNull
    java.lang.String functionName) {
    }
    
    public final void onMemoryClear() {
    }
    
    public final void onMemoryRecall() {
    }
    
    public final void onMemoryStore() {
    }
    
    public final void onMemoryAdd() {
    }
    
    public final void onMemorySubtract() {
    }
    
    public final void onHistoryItemSelect(@org.jetbrains.annotations.NotNull
    com.ritikthakur.rtcalc.data.local.HistoryEntity history) {
    }
    
    public final void deleteHistoryItem(@org.jetbrains.annotations.NotNull
    com.ritikthakur.rtcalc.data.local.HistoryEntity history) {
    }
    
    public final void clearAllHistory() {
    }
    
    public final void setSearchQuery(@org.jetbrains.annotations.NotNull
    java.lang.String query) {
    }
    
    public final void setThemeMode(@org.jetbrains.annotations.NotNull
    com.ritikthakur.rtcalc.data.repository.ThemeMode mode) {
    }
    
    public final void setHapticEnabled(boolean enabled) {
    }
    
    public final void setAngleMode(@org.jetbrains.annotations.NotNull
    com.ritikthakur.rtcalc.data.repository.AngleMode mode) {
    }
    
    public final void setDecimalPrecision(int precision) {
    }
    
    public final void setScientificNotation(boolean enabled) {
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