package com.bars.repository


import com.bars.BuildConfig

import com.bars.data.remote.ServicePlacesAPI
import com.bars.data.remote.ServiceVenuesAPI
import com.bars.util.Constants
import javax.inject.Inject

class PlacesRepository @Inject constructor(
    private val apiPlace: ServicePlacesAPI
) {
    suspend fun getNearbyPlaces(location: String) =
        apiPlace.getNearbyPlaces(BuildConfig.QUERY_PLACES, BuildConfig.RADIUS_PLACES,location, Constants.FIELDS)

}