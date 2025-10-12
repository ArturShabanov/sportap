package com.woonish.winwin.ui.screens.leagues

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woonish.winwin.data.local.entity.LeagueEntity
import com.woonish.winwin.data.repository.SportsRepository
import com.woonish.winwin.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltViewModel
class LeaguesViewModel @Inject constructor(
    private val repository: SportsRepository
) : ViewModel() {

    suspend fun refresh() {
        when (repository.refreshLeagues()) {
            is Resource.Error -> Unit
            is Resource.Loading -> Unit
            is Resource.Success -> Unit
        }
    }

    suspend fun getLeagues(sport: String): List<LeagueEntity> = withContext(Dispatchers.IO) {
        when (val res = repository.leaguesBySport(sport)) {
            is Resource.Success -> res.data
            else -> emptyList()
        }
    }
}


