package com.jrprofessor.platformcommons.response

import com.google.gson.annotations.SerializedName


class MovieListResponse {
    @SerializedName("results")
    val movie: List<Movie>? = null

    @SerializedName("page")
    val page: Int? = null

    @SerializedName("total_pages")
    val totalPages: Int? = null

    @SerializedName("total_results")
    val totalResults: Int? = null

}