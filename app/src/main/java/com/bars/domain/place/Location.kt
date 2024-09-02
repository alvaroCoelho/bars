package com.bars.domain.place

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Location(
    @SerializedName("address")
    val address: String
) : Serializable