package com.woonish.winwin.data.remote

import com.woonish.winwin.data.remote.dto.AllLeaguesResponse
import com.woonish.winwin.data.remote.dto.EventDetailsResponse
import com.woonish.winwin.data.remote.dto.EventsResponse
import com.woonish.winwin.data.remote.dto.PlayersResponse
import com.woonish.winwin.data.remote.dto.TeamDetailsResponse
import com.woonish.winwin.data.remote.dto.TeamsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TheSportsDbApi {

    // Search
    @GET("searchteams.php")
    suspend fun searchTeams(
        @Query("t") name: String
    ): TeamsResponse

    // Lists
    @GET("all_leagues.php")
    suspend fun getAllLeagues(): AllLeaguesResponse

    @GET("search_all_teams.php")
    suspend fun getTeamsByLeague(
        @Query("l") leagueName: String
    ): TeamsResponse

    // Lookups
    @GET("lookupteam.php")
    suspend fun lookupTeam(@Query("id") idTeam: String): TeamDetailsResponse

    @GET("lookup_all_players.php")
    suspend fun lookupAllPlayers(@Query("id") idTeam: String): PlayersResponse

    @GET("lookupevent.php")
    suspend fun lookupEvent(@Query("id") idEvent: String): EventDetailsResponse

    // Schedules: league
    @GET("eventsnextleague.php")
    suspend fun getNextLeagueEvents(@Query("id") idLeague: String): EventsResponse

    @GET("eventspastleague.php")
    suspend fun getPastLeagueEvents(@Query("id") idLeague: String): EventsResponse

    // Schedules: team
    @GET("eventsnext.php")
    suspend fun getNextTeamEvents(@Query("id") idTeam: String): EventsResponse

    @GET("eventslast.php")
    suspend fun getLastTeamEvents(@Query("id") idTeam: String): EventsResponse

    // By date
    @GET("eventsday.php")
    suspend fun getEventsByDay(
        @Query("d") date: String,
        @Query("s") sport: String? = null,
        @Query("l") leagueName: String? = null
    ): EventsResponse
}


