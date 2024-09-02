package com.bars.domain.place

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Hour(
    @SerializedName("display")
    val display: String,
    @SerializedName("open_now")
    val openNow: Boolean
) : Serializable