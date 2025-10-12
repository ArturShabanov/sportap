package com.woonish.winwin.ui.screens.search

import androidx.lifecycle.ViewModel
import com.woonish.winwin.data.local.entity.TeamEntity
import com.woonish.winwin.data.repository.SportsRepository
import com.woonish.winwin.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SportsRepository
) : ViewModel() {

    suspend fun search(query: String): List<TeamEntity> = withContext(Dispatchers.IO) {
        if (query.length < 3) return@withContext emptyList()
        // debounce
        delay(250)
        when (val r = repository.searchTeamsByName(query)) {
            is Resource.Success -> r.data
            else -> emptyList()
        }
    }
}


