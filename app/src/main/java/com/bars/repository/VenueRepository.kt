package com.bars.repository

import com.bars.BuildConfig
import com.bars.data.remote.ServicePlacesAPI
import com.bars.data.remote.ServiceVenuesAPI
import com.bars.di.DispatcherIO
import com.bars.util.Constants
import com.bars.util.Constants.TIPS_FIELDS
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class VenueRepository @Inject constructor(
    private val  apiPlace: ServicePlacesAPI
)
{
    suspend fun getTips(idVenue: String) =
        apiPlace.getPlaceTIps(
            idVenue,TIPS_FIELDS)
}