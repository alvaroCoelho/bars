package com.bars.domain.tips
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TipsModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("text")
    val review: String,
    @SerializedName("createdAt")
    val date: String,
    @SerializedName("photo")
    val tipPhoto: TipPhoto

) : Serializable

