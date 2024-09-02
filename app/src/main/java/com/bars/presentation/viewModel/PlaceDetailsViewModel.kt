package com.bars.presentation.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bars.domain.tips.TipsModel
import com.bars.domain.usesCases.GetTipsUseCase
import com.bars.presentation.screens.Routers.VENUE_ID
import com.bars.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
@HiltViewModel
class PlaceDetailsViewModel @Inject constructor(
private val getTipsUseCase: GetTipsUseCase,
savedStateHandle: SavedStateHandle
): ViewModel(){

    private val _list: MutableState<TipsResourceState> = mutableStateOf(TipsResourceState.Loading)
    val list: State<TipsResourceState> = _list


    init {

        fetch( checkNotNull(savedStateHandle[VENUE_ID]))
    }



    private fun fetch(idVenue: String) = viewModelScope.launch {
        getTips(idVenue)
    }

    private suspend fun getTips(idVenue: String) {

        try {
            val response = getTipsUseCase.invoke(idVenue)
            _list.value = handleResponse(response)

        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> _list.value = PlacesResourceState.Error(Constants.CONNECT_ERROR)
                else -> _list.value = PlacesResourceState.Error(Constants.DATA_ERROR)
            }
        }

    }



    private fun handleResponse(response: Response<List<TipsModel>>): TipsResourceState {
        if (response.isSuccessful) {
            response.body()?.let { values ->
                if (values.isEmpty())
                    return  TipsResourceState.Empty
                else
                    return TipsResourceState.Success(values)
            }
        }
        return TipsResourceState.Error(response.message())
    }

}

sealed interface TipsResourceState {

    object Loading : TipsResourceState

    class Success(
        val tips:List <TipsModel>
    ) : TipsResourceState

    class Error(
        val message: String
    ) : TipsResourceState

    object Empty : TipsResourceState
}