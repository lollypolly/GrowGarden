package ru.itis.polypollya.growgarden.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.itis.polypollya.growgarden.adapters.AllPlantsAdapter
import ru.itis.polypollya.growgarden.adapters.IndoorPlantsAdapter
import ru.itis.polypollya.growgarden.adapters.OutdoorPlantsAdapter
import ru.itis.polypollya.growgarden.data.FlowersDatabase
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRunningDatabase(@ApplicationContext app: Context) = Room.databaseBuilder(
        app,
        FlowersDatabase::class.java,
        "flowers_db"
    ).build()

    @Singleton
    @Provides
    fun provideRunDao(db: FlowersDatabase) = db.getFlowersDao()

    @Singleton
    @Provides
    fun provideAllPlantsAdapter() = AllPlantsAdapter()

    @Singleton
    @Provides
    fun provideIndoorPlantsAdapter() = IndoorPlantsAdapter()

    @Singleton
    @Provides
    fun provideOutdoorPlantsAdapter() = OutdoorPlantsAdapter()

    @Singleton
    @Provides
    fun provideMainPrefs(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("MAIN_PREFS", 0)
}