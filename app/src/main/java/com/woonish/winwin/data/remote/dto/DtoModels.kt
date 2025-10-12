package com.woonish.winwin.data.remote.dto

import com.squareup.moshi.Json

data class AllLeaguesResponse(
    @Json(name = "leagues") val leagues: List<LeagueDto>?
)

data class LeagueDto(
    @Json(name = "idLeague") val idLeague: String?,
    @Json(name = "strLeague") val strLeague: String?,
    @Json(name = "strSport") val strSport: String?,
    @Json(name = "strLeagueAlternate") val strLeagueAlternate: String?
)

data class TeamsResponse(
    @Json(name = "teams") val teams: List<TeamDto>?
)

data class TeamDetailsResponse(
    @Json(name = "teams") val teams: List<TeamDto>?
)

data class TeamDto(
    @Json(name = "idTeam") val idTeam: String?,
    @Json(name = "strTeam") val strTeam: String?,
    @Json(name = "strLeague") val strLeague: String?,
    @Json(name = "strSport") val strSport: String?,
    @Json(name = "intFormedYear") val intFormedYear: String?,
    @Json(name = "strStadium") val strStadium: String?,
    @Json(name = "strTeamBadge") val strTeamBadge: String?,
    @Json(name = "strTeamLogo") val strTeamLogo: String?,
    @Json(name = "strCountry") val strCountry: String?
)

data class PlayersResponse(
    @Json(name = "player") val players: List<PlayerDto>?
)

data class PlayerDto(
    @Json(name = "idPlayer") val idPlayer: String?,
    @Json(name = "strPlayer") val strPlayer: String?,
    @Json(name = "strPosition") val strPosition: String?,
    @Json(name = "dateBorn") val dateBorn: String?,
    @Json(name = "strNationality") val strNationality: String?,
    @Json(name = "strCutout") val strCutout: String?
)

data class EventsResponse(
    @Json(name = "events") val events: List<EventDto>?
)

data class EventDetailsResponse(
    @Json(name = "events") val events: List<EventDto>?
)

data class EventDto(
    @Json(name = "idEvent") val idEvent: String?,
    @Json(name = "strEvent") val strEvent: String?,
    @Json(name = "strSport") val strSport: String?,
    @Json(name = "strLeague") val strLeague: String?,
    @Json(name = "idLeague") val idLeague: String?,
    @Json(name = "dateEvent") val dateEvent: String?,
    @Json(name = "strTime") val strTime: String?,
    @Json(name = "strTimestamp") val strTimestamp: String?,
    @Json(name = "strStatus") val strStatus: String?,
    @Json(name = "strHomeTeam") val strHomeTeam: String?,
    @Json(name = "strAwayTeam") val strAwayTeam: String?,
    @Json(name = "idHomeTeam") val idHomeTeam: String?,
    @Json(name = "idAwayTeam") val idAwayTeam: String?,
    @Json(name = "intHomeScore") val intHomeScore: String?,
    @Json(name = "intAwayScore") val intAwayScore: String?,
    @Json(name = "strVenue") val strVenue: String?,
    @Json(name = "strSeason") val strSeason: String?,
    @Json(name = "strHomeTeamBadge") val strHomeTeamBadge: String?,
    @Json(name = "strAwayTeamBadge") val strAwayTeamBadge: String?
)


