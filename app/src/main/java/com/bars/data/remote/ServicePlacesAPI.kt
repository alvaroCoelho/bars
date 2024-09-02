package com.bars.data.remote

import com.bars.data.model.PlacesResponse
import com.bars.domain.tips.TipsModel


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ServicePlacesAPI {
    @GET("places/search")
    suspend fun getNearbyPlaces(
        @Query("query") query: String,
        @Query("radius") radius: String,
        @Query("ll") currentLocation: String,
       @Query("fields") field: String
        ): Response<PlacesResponse>

    @GET("places/{fsq_id}/tips")
    suspend fun getPlaceTIps(
        @Path("fsq_id") query: String,
        @Query("fields") field: String
    ): Response<List<TipsModel>>
}
