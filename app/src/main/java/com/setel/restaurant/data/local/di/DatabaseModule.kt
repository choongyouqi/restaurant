package com.setel.restaurant.data.local.di

import android.content.Context
import androidx.room.Room
import com.setel.restaurant.data.local.database.AppDatabase
import com.setel.restaurant.data.local.database.OperatingHoursDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideOperatingHoursDao(appDatabase: AppDatabase): OperatingHoursDao {
        return appDatabase.operatingHoursDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "OperatingHours"
        ).build()
    }
}
