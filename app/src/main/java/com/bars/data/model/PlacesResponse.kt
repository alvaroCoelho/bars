package com.bars.data.model

import com.bars.domain.place.Place
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PlacesResponse (
    @SerializedName("results")
    val results: List<Place>
) : Serializable