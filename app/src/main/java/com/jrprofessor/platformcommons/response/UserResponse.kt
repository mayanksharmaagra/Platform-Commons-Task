package com.jrprofessor.platformcommons.response

import com.google.gson.annotations.SerializedName


class UserResponse {
    @SerializedName("data")
    val user: List<User>? = null
}