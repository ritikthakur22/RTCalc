package com.ritikthakur.rtcalc.data.repository;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\r\b\u0007\u0018\u00002\u00020\u0001:\u0001%B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0019\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0007H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001bJ\u0019\u0010\u001c\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u000eH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001eJ\u0019\u0010\u001f\u001a\u00020\u00192\u0006\u0010 \u001a\u00020\u0011H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010!J\u0019\u0010\"\u001a\u00020\u00192\u0006\u0010 \u001a\u00020\u0011H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010!J\u0019\u0010#\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0016H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010$R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\tR\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\tR\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00110\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\tR\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\t\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006&"}, d2 = {"Lcom/ritikthakur/rtcalc/data/repository/SettingsRepository;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "angleModeFlow", "Lkotlinx/coroutines/flow/Flow;", "Lcom/ritikthakur/rtcalc/data/repository/AngleMode;", "getAngleModeFlow", "()Lkotlinx/coroutines/flow/Flow;", "dataStore", "Landroidx/datastore/core/DataStore;", "Landroidx/datastore/preferences/core/Preferences;", "decimalPrecisionFlow", "", "getDecimalPrecisionFlow", "hapticEnabledFlow", "", "getHapticEnabledFlow", "scientificNotationFlow", "getScientificNotationFlow", "themeModeFlow", "Lcom/ritikthakur/rtcalc/data/repository/ThemeMode;", "getThemeModeFlow", "setAngleMode", "", "mode", "(Lcom/ritikthakur/rtcalc/data/repository/AngleMode;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setDecimalPrecision", "precision", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setHapticEnabled", "enabled", "(ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setScientificNotation", "setThemeMode", "(Lcom/ritikthakur/rtcalc/data/repository/ThemeMode;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "PreferencesKeys", "app_release"})
public final class SettingsRepository {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> dataStore = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<com.ritikthakur.rtcalc.data.repository.ThemeMode> themeModeFlow = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.lang.Boolean> hapticEnabledFlow = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<com.ritikthakur.rtcalc.data.repository.AngleMode> angleModeFlow = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.lang.Integer> decimalPrecisionFlow = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.lang.Boolean> scientificNotationFlow = null;
    
    @javax.inject.Inject
    public SettingsRepository(@dagger.hilt.android.qualifiers.ApplicationContext
    @org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<com.ritikthakur.rtcalc.data.repository.ThemeMode> getThemeModeFlow() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.lang.Boolean> getHapticEnabledFlow() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<com.ritikthakur.rtcalc.data.repository.AngleMode> getAngleModeFlow() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> getDecimalPrecisionFlow() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.lang.Boolean> getScientificNotationFlow() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object setThemeMode(@org.jetbrains.annotations.NotNull
    com.ritikthakur.rtcalc.data.repository.ThemeMode mode, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object setHapticEnabled(boolean enabled, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object setAngleMode(@org.jetbrains.annotations.NotNull
    com.ritikthakur.rtcalc.data.repository.AngleMode mode, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object setDecimalPrecision(int precision, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object setScientificNotation(boolean enabled, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u00c2\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0007R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0007R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\f0\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0007R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0007\u00a8\u0006\u0012"}, d2 = {"Lcom/ritikthakur/rtcalc/data/repository/SettingsRepository$PreferencesKeys;", "", "()V", "ANGLE_MODE", "Landroidx/datastore/preferences/core/Preferences$Key;", "", "getANGLE_MODE", "()Landroidx/datastore/preferences/core/Preferences$Key;", "DECIMAL_PRECISION", "", "getDECIMAL_PRECISION", "HAPTIC_ENABLED", "", "getHAPTIC_ENABLED", "SCIENTIFIC_NOTATION", "getSCIENTIFIC_NOTATION", "THEME_MODE", "getTHEME_MODE", "app_release"})
    static final class PreferencesKeys {
        @org.jetbrains.annotations.NotNull
        private static final androidx.datastore.preferences.core.Preferences.Key<java.lang.String> THEME_MODE = null;
        @org.jetbrains.annotations.NotNull
        private static final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> HAPTIC_ENABLED = null;
        @org.jetbrains.annotations.NotNull
        private static final androidx.datastore.preferences.core.Preferences.Key<java.lang.String> ANGLE_MODE = null;
        @org.jetbrains.annotations.NotNull
        private static final androidx.datastore.preferences.core.Preferences.Key<java.lang.Integer> DECIMAL_PRECISION = null;
        @org.jetbrains.annotations.NotNull
        private static final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> SCIENTIFIC_NOTATION = null;
        @org.jetbrains.annotations.NotNull
        public static final com.ritikthakur.rtcalc.data.repository.SettingsRepository.PreferencesKeys INSTANCE = null;
        
        private PreferencesKeys() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final androidx.datastore.preferences.core.Preferences.Key<java.lang.String> getTHEME_MODE() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> getHAPTIC_ENABLED() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final androidx.datastore.preferences.core.Preferences.Key<java.lang.String> getANGLE_MODE() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final androidx.datastore.preferences.core.Preferences.Key<java.lang.Integer> getDECIMAL_PRECISION() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> getSCIENTIFIC_NOTATION() {
            return null;
        }
    }
}