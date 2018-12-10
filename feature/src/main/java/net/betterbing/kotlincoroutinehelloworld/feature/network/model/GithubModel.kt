package net.betterbing.kotlincoroutinehelloworld.feature.network.model

import kotlinx.coroutines.Deferred
import net.betterbing.kotlincoroutinehelloworld.feature.db.entity.Repos
import net.betterbing.kotlincoroutinehelloworld.feature.db.entity.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubModel {

    @GET("/users/{username}")
    fun getUserByUsername(@Path("username") userName: String): Deferred<Response<User>>

    @GET("users/{username}/repos")
    fun getUserRepos(@Path("username") userName: String): Deferred<Response<List<Repos>>>

}