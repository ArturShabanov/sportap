package com.woonish.winwin.ui.screens.events

import androidx.lifecycle.ViewModel
import com.woonish.winwin.data.local.entity.EventEntity
import com.woonish.winwin.data.repository.SportsRepository
import com.woonish.winwin.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val repository: SportsRepository
) : ViewModel() {

    suspend fun leagueNext(idLeague: String): List<EventEntity> = withContext(Dispatchers.IO) {
        when (val r = repository.refreshLeagueNextEvents(idLeague)) {
            is Resource.Success -> r.data
            else -> emptyList()
        }
    }

    suspend fun leaguePast(idLeague: String): List<EventEntity> = withContext(Dispatchers.IO) {
        when (val r = repository.refreshLeaguePastEvents(idLeague)) {
            is Resource.Success -> r.data
            else -> emptyList()
        }
    }

    suspend fun teamNext(idTeam: String): List<EventEntity> = withContext(Dispatchers.IO) {
        when (val r = repository.refreshTeamNextEvents(idTeam)) {
            is Resource.Success -> r.data
            else -> emptyList()
        }
    }

    suspend fun teamLast(idTeam: String): List<EventEntity> = withContext(Dispatchers.IO) {
        when (val r = repository.refreshTeamLastEvents(idTeam)) {
            is Resource.Success -> r.data
            else -> emptyList()
        }
    }

    suspend fun byDay(date: String, sport: String?, leagueName: String?): List<EventEntity> = withContext(Dispatchers.IO) {
        when (val r = repository.refreshEventsByDay(date, sport, leagueName)) {
            is Resource.Success -> r.data
            else -> emptyList()
        }
    }
}

fun ScheduleViewModel.scopeLoadByDay(date: String, sport: String?, leagueName: String?, onLoaded: (List<EventEntity>) -> Unit) {
    viewModelScope.launch {
        val data = byDay(date, sport, leagueName)
        onLoaded(data)
    }
}


