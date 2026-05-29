package com.ritikthakur.rtcalc.ui.viewmodel;

import androidx.lifecycle.SavedStateHandle;
import com.ritikthakur.rtcalc.data.repository.HistoryRepository;
import com.ritikthakur.rtcalc.data.repository.SettingsRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class CalculatorViewModel_Factory implements Factory<CalculatorViewModel> {
  private final Provider<HistoryRepository> historyRepositoryProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public CalculatorViewModel_Factory(Provider<HistoryRepository> historyRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.historyRepositoryProvider = historyRepositoryProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public CalculatorViewModel get() {
    return newInstance(historyRepositoryProvider.get(), settingsRepositoryProvider.get(), savedStateHandleProvider.get());
  }

  public static CalculatorViewModel_Factory create(
      Provider<HistoryRepository> historyRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new CalculatorViewModel_Factory(historyRepositoryProvider, settingsRepositoryProvider, savedStateHandleProvider);
  }

  public static CalculatorViewModel newInstance(HistoryRepository historyRepository,
      SettingsRepository settingsRepository, SavedStateHandle savedStateHandle) {
    return new CalculatorViewModel(historyRepository, settingsRepository, savedStateHandle);
  }
}
