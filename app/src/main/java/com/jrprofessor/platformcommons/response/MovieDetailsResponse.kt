package com.jrprofessor.platformcommons.response

import com.google.gson.annotations.SerializedName

class MovieDetailsResponse {
    @SerializedName("title")
    val title: String? = null

    @SerializedName("release_date")
    val releaseDate: String? = null

    @SerializedName("poster_path")
    val posterPath: String? = null

    @SerializedName("overview")
    val overview: String? = null
}