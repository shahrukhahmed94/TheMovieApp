package com.shahrukh.movieapp.data.remote.response


import com.google.gson.annotations.SerializedName
import com.shahrukh.movieapp.model.Genre

data class GenreResponse(
    @SerializedName("genres")
    val genres: List<Genre>
)