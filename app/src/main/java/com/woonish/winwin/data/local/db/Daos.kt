package com.woonish.winwin.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.woonish.winwin.data.local.entity.EventEntity
import com.woonish.winwin.data.local.entity.LeagueEntity
import com.woonish.winwin.data.local.entity.PlayerEntity
import com.woonish.winwin.data.local.entity.TeamEntity

@Dao
interface LeaguesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(leagues: List<LeagueEntity>)

    @Query("SELECT * FROM leagues WHERE strSport = :sport ORDER BY strLeague ASC")
    suspend fun leaguesBySport(sport: String): List<LeagueEntity>
}

@Dao
interface TeamsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(teams: List<TeamEntity>)

    @Query("SELECT * FROM teams WHERE strLeague = :league ORDER BY strTeam ASC")
    suspend fun teamsByLeague(league: String): List<TeamEntity>
}

@Dao
interface PlayersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(players: List<PlayerEntity>)

    @Query("SELECT * FROM players WHERE idTeam = :idTeam ORDER BY strPlayer ASC")
    suspend fun playersByTeam(idTeam: String): List<PlayerEntity>
}

@Dao
interface EventsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(events: List<EventEntity>)

    @Query("SELECT * FROM events WHERE idLeague = :idLeague ORDER BY dateEvent DESC")
    suspend fun eventsByLeague(idLeague: String): List<EventEntity>

    @Query("SELECT * FROM events WHERE idHomeTeam = :idTeam OR idAwayTeam = :idTeam ORDER BY dateEvent DESC")
    suspend fun eventsByTeam(idTeam: String): List<EventEntity>
}


