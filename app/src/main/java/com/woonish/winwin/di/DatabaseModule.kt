package com.woonish.winwin.di

import android.content.Context
import androidx.room.Room
import com.woonish.winwin.data.local.db.AppDatabase
import com.woonish.winwin.data.local.db.EventsDao
import com.woonish.winwin.data.local.db.LeaguesDao
import com.woonish.winwin.data.local.db.PlayersDao
import com.woonish.winwin.data.local.db.TeamsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "winwin.db").build()

    @Provides fun provideLeaguesDao(db: AppDatabase): LeaguesDao = db.leaguesDao()
    @Provides fun provideTeamsDao(db: AppDatabase): TeamsDao = db.teamsDao()
    @Provides fun providePlayersDao(db: AppDatabase): PlayersDao = db.playersDao()
    @Provides fun provideEventsDao(db: AppDatabase): EventsDao = db.eventsDao()
}


