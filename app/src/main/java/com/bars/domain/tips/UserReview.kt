package com.bars.domain.tips


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserReview (
    @SerializedName("id")
    val id: String,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("tipPhoto")
    val photo: TipPhoto
) : Serializable
