package com.ritikthakur.rtcalc.di;

import android.content.Context;
import com.ritikthakur.rtcalc.data.local.HistoryDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class AppModule_ProvideHistoryDatabaseFactory implements Factory<HistoryDatabase> {
  private final Provider<Context> contextProvider;

  public AppModule_ProvideHistoryDatabaseFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public HistoryDatabase get() {
    return provideHistoryDatabase(contextProvider.get());
  }

  public static AppModule_ProvideHistoryDatabaseFactory create(Provider<Context> contextProvider) {
    return new AppModule_ProvideHistoryDatabaseFactory(contextProvider);
  }

  public static HistoryDatabase provideHistoryDatabase(Context context) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideHistoryDatabase(context));
  }
}
