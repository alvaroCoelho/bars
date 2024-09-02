package com.bars.domain.place

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Category(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
) : Serializable
