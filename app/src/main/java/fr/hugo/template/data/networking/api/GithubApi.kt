package fr.hugo.template.data.networking.api

import fr.hugo.template.data.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi{

    @GET(PATH_GET_USERS)
    suspend fun getAllUsers(
        @Header("Authorization") accessToken: String,
        @Query("per_page") perPage: Int,
        @Query("since") since: Int

    ): Response<List<User>>

    @GET(GET_USER_PATH)
    suspend fun getUserDetails(
        @Header("Authorization") accessToken: String,
        @Path("id") id: String
    ): Response<User>

    companion object {
        const val PATH_GET_USERS = "users"
        const val GET_USER_PATH ="users/{id}"

    }
}