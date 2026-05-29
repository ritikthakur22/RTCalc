package com.ritikthakur.rtcalc.di;

import com.ritikthakur.rtcalc.data.local.HistoryDao;
import com.ritikthakur.rtcalc.data.local.HistoryDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class AppModule_ProvideHistoryDaoFactory implements Factory<HistoryDao> {
  private final Provider<HistoryDatabase> databaseProvider;

  public AppModule_ProvideHistoryDaoFactory(Provider<HistoryDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public HistoryDao get() {
    return provideHistoryDao(databaseProvider.get());
  }

  public static AppModule_ProvideHistoryDaoFactory create(
      Provider<HistoryDatabase> databaseProvider) {
    return new AppModule_ProvideHistoryDaoFactory(databaseProvider);
  }

  public static HistoryDao provideHistoryDao(HistoryDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideHistoryDao(database));
  }
}
