package com.woonish.winwin.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "leagues")
data class LeagueEntity(
    @PrimaryKey val idLeague: String,
    val strLeague: String?,
    val strSport: String?,
    val updatedAt: Long
)

@Entity(tableName = "teams")
data class TeamEntity(
    @PrimaryKey val idTeam: String,
    val strTeam: String?,
    val strLeague: String?,
    val strSport: String?,
    val intFormedYear: String?,
    val strStadium: String?,
    val strTeamBadge: String?,
    val strTeamLogo: String?,
    val strCountry: String?,
    val updatedAt: Long
)

@Entity(tableName = "players")
data class PlayerEntity(
    @PrimaryKey val idPlayer: String,
    val strPlayer: String?,
    val strPosition: String?,
    val dateBorn: String?,
    val strNationality: String?,
    val strCutout: String?,
    val idTeam: String?,
    val updatedAt: Long
)

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey val idEvent: String,
    val strEvent: String?,
    val strSport: String?,
    val strLeague: String?,
    val idLeague: String?,
    val dateEvent: String?,
    val strTime: String?,
    val strTimestamp: String?,
    val strStatus: String?,
    val strHomeTeam: String?,
    val strAwayTeam: String?,
    val idHomeTeam: String?,
    val idAwayTeam: String?,
    val intHomeScore: String?,
    val intAwayScore: String?,
    val strVenue: String?,
    val strSeason: String?,
    val strHomeTeamBadge: String?,
    val strAwayTeamBadge: String?,
    val updatedAt: Long
)


