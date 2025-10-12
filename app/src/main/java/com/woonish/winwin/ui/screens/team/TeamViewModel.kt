package com.woonish.winwin.ui.screens.team

import androidx.lifecycle.ViewModel
import com.woonish.winwin.data.local.entity.TeamEntity
import com.woonish.winwin.data.repository.SportsRepository
import com.woonish.winwin.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltViewModel
class TeamViewModel @Inject constructor(
    private val repository: SportsRepository
) : ViewModel() {

    suspend fun loadTeam(idTeam: String): TeamEntity? = withContext(Dispatchers.IO) {
        when (val r = repository.teamById(idTeam)) {
            is Resource.Success -> r.data
            else -> null
        }
    }
}


