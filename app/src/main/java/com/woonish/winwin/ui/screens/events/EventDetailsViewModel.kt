package com.woonish.winwin.ui.screens.events

import androidx.lifecycle.ViewModel
import com.woonish.winwin.data.local.entity.EventEntity
import com.woonish.winwin.data.repository.SportsRepository
import com.woonish.winwin.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltViewModel
class EventDetailsViewModel @Inject constructor(
    private val repository: SportsRepository
) : ViewModel() {

    suspend fun loadEvent(idEvent: String): EventEntity? = withContext(Dispatchers.IO) {
        when (val r = repository.eventById(idEvent)) {
            is Resource.Success -> r.data
            else -> null
        }
    }
}


