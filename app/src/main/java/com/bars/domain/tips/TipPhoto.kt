package com.bars.domain.tips

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TipPhoto(
    @SerializedName("prefix")
    val prefix: String,
    @SerializedName("suffix")
    val suffix: String
) : Serializable