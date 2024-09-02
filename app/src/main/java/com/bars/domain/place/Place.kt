package com.bars.domain.place
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Place(
    @SerializedName("fsq_id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("hours")
    val hour: Hour,
    @SerializedName("location")
    val location: Location,
    @SerializedName("price")
    val price: String,
    @SerializedName("rating")
    val rating: String,
    @SerializedName("distance")
    val distance: String,
    @SerializedName("tel")
    val phone: String,
    @SerializedName("photos")
    val photos: List<Photo>

) : Serializable

