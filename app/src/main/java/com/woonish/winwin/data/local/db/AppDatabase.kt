package com.woonish.winwin.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.woonish.winwin.data.local.entity.EventEntity
import com.woonish.winwin.data.local.entity.LeagueEntity
import com.woonish.winwin.data.local.entity.PlayerEntity
import com.woonish.winwin.data.local.entity.TeamEntity

@Database(
    entities = [LeagueEntity::class, TeamEntity::class, PlayerEntity::class, EventEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun leaguesDao(): LeaguesDao
    abstract fun teamsDao(): TeamsDao
    abstract fun playersDao(): PlayersDao
    abstract fun eventsDao(): EventsDao
}


