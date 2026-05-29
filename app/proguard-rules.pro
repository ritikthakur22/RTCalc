# Proguard rules for com.ritikthakur.rtcalc

# Keep Room database components
-keep class * extends androidx.room.RoomDatabase
-dontwarn androidx.room.limit.**

# Keep Hilt / Dagger components
-keep class * extends dagger.hilt.components.SingletonComponent
-keep @dagger.hilt.EntryPoint class * { *; }

# Keep our Room entities to ensure they can be mapped from JSON/Cursor
-keep class com.ritikthakur.rtcalc.data.local.HistoryEntity { *; }

# Keep ViewModels and their constructors
-keepclassmembers class * extends androidx.lifecycle.ViewModel {
    public <init>(...);
}
