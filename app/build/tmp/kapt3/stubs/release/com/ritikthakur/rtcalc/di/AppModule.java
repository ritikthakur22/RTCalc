package com.ritikthakur.rtcalc.di;

@dagger.Module
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0007J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0012\u0010\u000b\u001a\u00020\b2\b\b\u0001\u0010\f\u001a\u00020\rH\u0007\u00a8\u0006\u000e"}, d2 = {"Lcom/ritikthakur/rtcalc/di/AppModule;", "", "()V", "provideCurrencyApi", "Lcom/ritikthakur/rtcalc/data/remote/CurrencyApi;", "provideCurrencyDao", "Lcom/ritikthakur/rtcalc/data/local/CurrencyDao;", "database", "Lcom/ritikthakur/rtcalc/data/local/HistoryDatabase;", "provideHistoryDao", "Lcom/ritikthakur/rtcalc/data/local/HistoryDao;", "provideHistoryDatabase", "context", "Landroid/content/Context;", "app_release"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class AppModule {
    @org.jetbrains.annotations.NotNull
    public static final com.ritikthakur.rtcalc.di.AppModule INSTANCE = null;
    
    private AppModule() {
        super();
    }
    
    @dagger.Provides
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public final com.ritikthakur.rtcalc.data.local.HistoryDatabase provideHistoryDatabase(@dagger.hilt.android.qualifiers.ApplicationContext
    @org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public final com.ritikthakur.rtcalc.data.local.HistoryDao provideHistoryDao(@org.jetbrains.annotations.NotNull
    com.ritikthakur.rtcalc.data.local.HistoryDatabase database) {
        return null;
    }
    
    @dagger.Provides
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public final com.ritikthakur.rtcalc.data.local.CurrencyDao provideCurrencyDao(@org.jetbrains.annotations.NotNull
    com.ritikthakur.rtcalc.data.local.HistoryDatabase database) {
        return null;
    }
    
    @dagger.Provides
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public final com.ritikthakur.rtcalc.data.remote.CurrencyApi provideCurrencyApi() {
        return null;
    }
}