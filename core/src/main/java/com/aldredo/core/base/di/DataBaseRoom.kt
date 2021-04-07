package com.aldredo.core.base.di

import android.app.Application
import androidx.room.Room
import com.aldredo.core.base.room.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseRoom {
    @Provides
    @Singleton
    fun provideDataBase(context: Application): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "database")
            .build()
    }
}