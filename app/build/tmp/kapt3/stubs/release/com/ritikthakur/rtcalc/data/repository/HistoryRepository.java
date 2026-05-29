package com.ritikthakur.rtcalc.data.repository;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0011\u0010\u0005\u001a\u00020\u0006H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0007J\u0019\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\nH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ\u0012\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u000e0\rJ!\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0014"}, d2 = {"Lcom/ritikthakur/rtcalc/data/repository/HistoryRepository;", "", "historyDao", "Lcom/ritikthakur/rtcalc/data/local/HistoryDao;", "(Lcom/ritikthakur/rtcalc/data/local/HistoryDao;)V", "clearHistory", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteHistory", "history", "Lcom/ritikthakur/rtcalc/data/local/HistoryEntity;", "(Lcom/ritikthakur/rtcalc/data/local/HistoryEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllHistory", "Lkotlinx/coroutines/flow/Flow;", "", "insertHistory", "expression", "", "result", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_release"})
public final class HistoryRepository {
    @org.jetbrains.annotations.NotNull
    private final com.ritikthakur.rtcalc.data.local.HistoryDao historyDao = null;
    
    @javax.inject.Inject
    public HistoryRepository(@org.jetbrains.annotations.NotNull
    com.ritikthakur.rtcalc.data.local.HistoryDao historyDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.ritikthakur.rtcalc.data.local.HistoryEntity>> getAllHistory() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object insertHistory(@org.jetbrains.annotations.NotNull
    java.lang.String expression, @org.jetbrains.annotations.NotNull
    java.lang.String result, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object deleteHistory(@org.jetbrains.annotations.NotNull
    com.ritikthakur.rtcalc.data.local.HistoryEntity history, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object clearHistory(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}