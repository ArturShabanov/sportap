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
}


