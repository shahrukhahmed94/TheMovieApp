package com.shahrukh.movieapp.data.remote.response


import com.google.gson.annotations.SerializedName
import com.shahrukh.movieapp.model.Cast

data class CastResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("cast")
    val castResult: List<Cast>
)