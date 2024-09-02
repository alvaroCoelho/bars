package com.bars.presentation.viewModel

import android.location.Location
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bars.data.model.PlacesResponse
import com.bars.domain.place.Place
import com.bars.domain.usesCases.GetNearbyPlacesUseCase
import com.bars.domain.usesCases.GetUserLocationUseCase
import com.bars.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ListPlacesViewModel @Inject constructor(
private val getNearbyPlacesUseCase: GetNearbyPlacesUseCase,
    private val getUserLocationUseCase: GetUserLocationUseCase
): ViewModel() {

    private val _list: MutableState<PlacesResourceState> = mutableStateOf(PlacesResourceState.Loading)
    val list: State<PlacesResourceState> = _list

    private val lock = Mutex()
    var location: Location? = null
    init {
        fetch()
    }


    private fun fetch() = viewModelScope.launch {

        lock.withLock {

            location = getUserLocationUseCase.invoke()
            getNearbyPlaces("${location?.latitude},${location?.longitude}")

        }
    }

    private suspend fun getNearbyPlaces(location: String) {

        try {
            val response = getNearbyPlacesUseCase.invoke(location)
            _list.value = handleResponse(response)

        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> _list.value = PlacesResourceState.Error(Constants.CONNECT_ERROR)
                else -> _list.value = PlacesResourceState.Error(Constants.DATA_ERROR)
            }
        }

    }

    private fun handleResponse(response: Response<PlacesResponse>): PlacesResourceState {
        if (response.isSuccessful) {
            response.body()?.let { values ->
                if (values.results.isEmpty())
                    return  PlacesResourceState.Empty
                else
                return PlacesResourceState.Success(values.results)
            }
        }
        return PlacesResourceState.Error(response.message())
    }
}

sealed interface PlacesResourceState {

    object Loading : PlacesResourceState

    class Success(
        val places: List<Place>
    ) : PlacesResourceState

    class Error(
        val message: String
    ) : PlacesResourceState, TipsResourceState

    object Empty : PlacesResourceState
}