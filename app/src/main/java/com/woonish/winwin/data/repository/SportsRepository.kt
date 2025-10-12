package com.woonish.winwin.data.repository

import com.woonish.winwin.data.local.db.EventsDao
import com.woonish.winwin.data.local.db.LeaguesDao
import com.woonish.winwin.data.local.db.PlayersDao
import com.woonish.winwin.data.local.db.TeamsDao
import com.woonish.winwin.data.local.entity.EventEntity
import com.woonish.winwin.data.local.entity.LeagueEntity
import com.woonish.winwin.data.local.entity.PlayerEntity
import com.woonish.winwin.data.local.entity.TeamEntity
import com.woonish.winwin.data.remote.TheSportsDbApi
import com.woonish.winwin.util.Resource
import javax.inject.Inject

interface SportsRepository {
    suspend fun refreshLeagues(): Resource<Unit>
    suspend fun leaguesBySport(sport: String): Resource<List<LeagueEntity>>
    suspend fun refreshLeagueNextEvents(idLeague: String): Resource<List<EventEntity>>
    suspend fun refreshLeaguePastEvents(idLeague: String): Resource<List<EventEntity>>
    suspend fun refreshTeamNextEvents(idTeam: String): Resource<List<EventEntity>>
    suspend fun refreshTeamLastEvents(idTeam: String): Resource<List<EventEntity>>
    suspend fun refreshEventsByDay(date: String, sport: String? = null, leagueName: String? = null): Resource<List<EventEntity>>
    suspend fun eventById(idEvent: String): Resource<EventEntity?>
    suspend fun teamById(idTeam: String): Resource<TeamEntity?>
    suspend fun searchTeamsByName(name: String): Resource<List<TeamEntity>>
}

class SportsRepositoryImpl @Inject constructor(
    private val api: TheSportsDbApi,
    private val leaguesDao: LeaguesDao,
    private val teamsDao: TeamsDao,
    private val playersDao: PlayersDao,
    private val eventsDao: EventsDao
) : SportsRepository {

    override suspend fun refreshLeagues(): Resource<Unit> = try {
        val response = api.getAllLeagues()
        val now = System.currentTimeMillis()
        val entities = response.leagues.orEmpty()
            .filter { it.idLeague != null }
            .map {
                LeagueEntity(
                    idLeague = it.idLeague!!,
                    strLeague = it.strLeague,
                    strSport = it.strSport,
                    updatedAt = now
                )
            }
        leaguesDao.upsertAll(entities)
        Resource.Success(Unit)
    } catch (t: Throwable) {
        Resource.Error(t)
    }

    override suspend fun leaguesBySport(sport: String): Resource<List<LeagueEntity>> = try {
        val data = leaguesDao.leaguesBySport(sport)
        Resource.Success(data)
    } catch (t: Throwable) {
        Resource.Error(t)
    }

    private fun mapEventsToEntities(list: List<com.woonish.winwin.data.remote.dto.EventDto>?, now: Long): List<EventEntity> =
        list.orEmpty().filter { it.idEvent != null }.map {
            EventEntity(
                idEvent = it.idEvent!!,
                strEvent = it.strEvent,
                strSport = it.strSport,
                strLeague = it.strLeague,
                idLeague = it.idLeague,
                dateEvent = it.dateEvent,
                strTime = it.strTime,
                strTimestamp = it.strTimestamp,
                strStatus = it.strStatus,
                strHomeTeam = it.strHomeTeam,
                strAwayTeam = it.strAwayTeam,
                idHomeTeam = it.idHomeTeam,
                idAwayTeam = it.idAwayTeam,
                intHomeScore = it.intHomeScore,
                intAwayScore = it.intAwayScore,
                strVenue = it.strVenue,
                strSeason = it.strSeason,
                strHomeTeamBadge = it.strHomeTeamBadge,
                strAwayTeamBadge = it.strAwayTeamBadge,
                updatedAt = now
            )
        }

    override suspend fun refreshLeagueNextEvents(idLeague: String): Resource<List<EventEntity>> = try {
        val now = System.currentTimeMillis()
        val resp = api.getNextLeagueEvents(idLeague)
        val entities = mapEventsToEntities(resp.events, now)
        eventsDao.upsertAll(entities)
        Resource.Success(eventsDao.eventsByLeague(idLeague))
    } catch (t: Throwable) { Resource.Error(t) }

    override suspend fun refreshLeaguePastEvents(idLeague: String): Resource<List<EventEntity>> = try {
        val now = System.currentTimeMillis()
        val resp = api.getPastLeagueEvents(idLeague)
        val entities = mapEventsToEntities(resp.events, now)
        eventsDao.upsertAll(entities)
        Resource.Success(eventsDao.eventsByLeague(idLeague))
    } catch (t: Throwable) { Resource.Error(t) }

    override suspend fun refreshTeamNextEvents(idTeam: String): Resource<List<EventEntity>> = try {
        val now = System.currentTimeMillis()
        val resp = api.getNextTeamEvents(idTeam)
        val entities = mapEventsToEntities(resp.events, now)
        eventsDao.upsertAll(entities)
        Resource.Success(eventsDao.eventsByTeam(idTeam))
    } catch (t: Throwable) { Resource.Error(t) }

    override suspend fun refreshTeamLastEvents(idTeam: String): Resource<List<EventEntity>> = try {
        val now = System.currentTimeMillis()
        val resp = api.getLastTeamEvents(idTeam)
        val entities = mapEventsToEntities(resp.events, now)
        eventsDao.upsertAll(entities)
        Resource.Success(eventsDao.eventsByTeam(idTeam))
    } catch (t: Throwable) { Resource.Error(t) }

    override suspend fun refreshEventsByDay(date: String, sport: String?, leagueName: String?): Resource<List<EventEntity>> = try {
        val now = System.currentTimeMillis()
        val resp = api.getEventsByDay(date, sport, leagueName)
        val entities = mapEventsToEntities(resp.events, now)
        eventsDao.upsertAll(entities)
        // No strict filter here; simple return last fetched by league/team not applicable; return all by date not stored; return entities
        Resource.Success(entities)
    } catch (t: Throwable) { Resource.Error(t) }

    override suspend fun eventById(idEvent: String): Resource<EventEntity?> = try {
        val now = System.currentTimeMillis()
        val resp = api.lookupEvent(idEvent)
        val entities = mapEventsToEntities(resp.events, now)
        if (entities.isNotEmpty()) eventsDao.upsertAll(entities)
        Resource.Success(entities.firstOrNull())
    } catch (t: Throwable) { Resource.Error(t) }

    override suspend fun teamById(idTeam: String): Resource<TeamEntity?> = try {
        val local = teamsDao.teamById(idTeam)
        if (local != null) return Resource.Success(local)
        val now = System.currentTimeMillis()
        val resp = api.lookupTeam(idTeam)
        val team = resp.teams?.firstOrNull()
        val entity = team?.idTeam?.let {
            TeamEntity(
                idTeam = it,
                strTeam = team.strTeam,
                strLeague = team.strLeague,
                strSport = team.strSport,
                intFormedYear = team.intFormedYear,
                strStadium = team.strStadium,
                strTeamBadge = team.strTeamBadge,
                strTeamLogo = team.strTeamLogo,
                strCountry = team.strCountry,
                updatedAt = now
            )
        }
        if (entity != null) teamsDao.upsertAll(listOf(entity))
        Resource.Success(entity)
    } catch (t: Throwable) { Resource.Error(t) }

    override suspend fun searchTeamsByName(name: String): Resource<List<TeamEntity>> = try {
        val now = System.currentTimeMillis()
        val resp = api.searchTeams(name)
        val list = resp.teams.orEmpty().filter { it.idTeam != null }.map { t ->
            TeamEntity(
                idTeam = t.idTeam!!,
                strTeam = t.strTeam,
                strLeague = t.strLeague,
                strSport = t.strSport,
                intFormedYear = t.intFormedYear,
                strStadium = t.strStadium,
                strTeamBadge = t.strTeamBadge,
                strTeamLogo = t.strTeamLogo,
                strCountry = t.strCountry,
                updatedAt = now
            )
        }
        if (list.isNotEmpty()) teamsDao.upsertAll(list)
        Resource.Success(list)
    } catch (t: Throwable) { Resource.Error(t) }
}


