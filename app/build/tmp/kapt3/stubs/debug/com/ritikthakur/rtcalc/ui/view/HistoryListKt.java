package com.ritikthakur.rtcalc.ui.view;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000.\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a\u0012\u0010\u0000\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u0007\u001aN\u0010\u0004\u001a\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u000bH\u0007\u001a,\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u00072\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\u000bH\u0007\u00a8\u0006\u0013"}, d2 = {"BoxEmptyHistory", "", "modifier", "Landroidx/compose/ui/Modifier;", "HistoryBottomSheet", "historyList", "", "Lcom/ritikthakur/rtcalc/data/local/HistoryEntity;", "sheetState", "Landroidx/compose/material3/SheetState;", "onDismissRequest", "Lkotlin/Function0;", "onHistoryItemClick", "Lkotlin/Function1;", "onClearHistoryClick", "HistoryRowItem", "item", "onClick", "onLongClick", "app_debug"})
public final class HistoryListKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void HistoryBottomSheet(@org.jetbrains.annotations.NotNull
    java.util.List<com.ritikthakur.rtcalc.data.local.HistoryEntity> historyList, @org.jetbrains.annotations.NotNull
    androidx.compose.material3.SheetState sheetState, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismissRequest, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.ritikthakur.rtcalc.data.local.HistoryEntity, kotlin.Unit> onHistoryItemClick, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onClearHistoryClick) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void BoxEmptyHistory(@org.jetbrains.annotations.NotNull
    androidx.compose.ui.Modifier modifier) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.foundation.ExperimentalFoundationApi.class})
    @androidx.compose.runtime.Composable
    public static final void HistoryRowItem(@org.jetbrains.annotations.NotNull
    com.ritikthakur.rtcalc.data.local.HistoryEntity item, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onLongClick) {
    }
}