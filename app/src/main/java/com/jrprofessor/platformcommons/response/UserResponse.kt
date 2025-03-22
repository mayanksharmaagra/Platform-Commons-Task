package com.jrprofessor.platformcommons.response

import com.google.gson.annotations.SerializedName


class UserResponse {
    @SerializedName("data")
    val user: List<User>? = null

    @SerializedName("page")
    val page: Int? = null

    @SerializedName("per_page")
    val perPage: Int? = null

    @SerializedName("support")
    val support: Support? = null

    @SerializedName("total")
    val total: Int? = null

    @SerializedName("total_pages")
    val totalPages: Int=0
}