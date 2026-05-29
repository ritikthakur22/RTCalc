package com.ritikthakur.rtcalc.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class HistoryDatabase_Impl extends HistoryDatabase {
  private volatile HistoryDao _historyDao;

  private volatile CurrencyDao _currencyDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `calculation_history` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `expression` TEXT NOT NULL, `result` TEXT NOT NULL, `timestamp` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `currency_rates` (`baseCurrency` TEXT NOT NULL, `ratesJson` TEXT NOT NULL, `lastUpdated` INTEGER NOT NULL, PRIMARY KEY(`baseCurrency`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '1b1dc090b26dc282eece5ead4cf16d1a')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `calculation_history`");
        db.execSQL("DROP TABLE IF EXISTS `currency_rates`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsCalculationHistory = new HashMap<String, TableInfo.Column>(4);
        _columnsCalculationHistory.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCalculationHistory.put("expression", new TableInfo.Column("expression", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCalculationHistory.put("result", new TableInfo.Column("result", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCalculationHistory.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCalculationHistory = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCalculationHistory = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCalculationHistory = new TableInfo("calculation_history", _columnsCalculationHistory, _foreignKeysCalculationHistory, _indicesCalculationHistory);
        final TableInfo _existingCalculationHistory = TableInfo.read(db, "calculation_history");
        if (!_infoCalculationHistory.equals(_existingCalculationHistory)) {
          return new RoomOpenHelper.ValidationResult(false, "calculation_history(com.ritikthakur.rtcalc.data.local.HistoryEntity).\n"
                  + " Expected:\n" + _infoCalculationHistory + "\n"
                  + " Found:\n" + _existingCalculationHistory);
        }
        final HashMap<String, TableInfo.Column> _columnsCurrencyRates = new HashMap<String, TableInfo.Column>(3);
        _columnsCurrencyRates.put("baseCurrency", new TableInfo.Column("baseCurrency", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCurrencyRates.put("ratesJson", new TableInfo.Column("ratesJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCurrencyRates.put("lastUpdated", new TableInfo.Column("lastUpdated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCurrencyRates = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCurrencyRates = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCurrencyRates = new TableInfo("currency_rates", _columnsCurrencyRates, _foreignKeysCurrencyRates, _indicesCurrencyRates);
        final TableInfo _existingCurrencyRates = TableInfo.read(db, "currency_rates");
        if (!_infoCurrencyRates.equals(_existingCurrencyRates)) {
          return new RoomOpenHelper.ValidationResult(false, "currency_rates(com.ritikthakur.rtcalc.data.local.CurrencyRateEntity).\n"
                  + " Expected:\n" + _infoCurrencyRates + "\n"
                  + " Found:\n" + _existingCurrencyRates);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "1b1dc090b26dc282eece5ead4cf16d1a", "2f2d683c4c556d32f3a78f8cc0e26b9c");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "calculation_history","currency_rates");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `calculation_history`");
      _db.execSQL("DELETE FROM `currency_rates`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(HistoryDao.class, HistoryDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CurrencyDao.class, CurrencyDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public HistoryDao historyDao() {
    if (_historyDao != null) {
      return _historyDao;
    } else {
      synchronized(this) {
        if(_historyDao == null) {
          _historyDao = new HistoryDao_Impl(this);
        }
        return _historyDao;
      }
    }
  }

  @Override
  public CurrencyDao currencyDao() {
    if (_currencyDao != null) {
      return _currencyDao;
    } else {
      synchronized(this) {
        if(_currencyDao == null) {
          _currencyDao = new CurrencyDao_Impl(this);
        }
        return _currencyDao;
      }
    }
  }
}
