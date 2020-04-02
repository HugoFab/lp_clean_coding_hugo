package fr.hugo.template.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import fr.hugo.template.data.model.User
import fr.hugo.template.data.networking.HttpClientManager
import fr.hugo.template.data.networking.api.GithubApi
import fr.hugo.template.data.networking.createApi
import fr.hugo.template.data.networking.datasource.UserDataSource

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


private class UserRepositoryImpl(
    private val api: GithubApi
) : UserRepository {

    private val paginationConfig = PagedList.Config
        .Builder()
        .setEnablePlaceholders(false)
        .setPageSize(20)
        .build()

    override fun getPaginatedList(
        scope: CoroutineScope
    ): LiveData<PagedList<User>> {
        return LivePagedListBuilder(
            UserDataSource.Factory(api, scope),
            paginationConfig
        ).build()
    }

    override suspend fun getUserDetails(id: String): User? {
        return withContext(Dispatchers.IO) {

            val response = api.getUserDetails(ACCESS_TOKEN, id)
            check(response.isSuccessful) { "Response is not a success : code = ${response.code()}" }
            response.body() ?: throw IllegalStateException("Body is null")
        }
    }


    companion object {
        private const val ACCESS_TOKEN : String ="a57aa7a5d6a8b277253f0ff2bade02b2c61a9b99"
    }

}


interface UserRepository {


    fun getPaginatedList(scope: CoroutineScope): LiveData<PagedList<User>>

    suspend fun getUserDetails(id: String): User?




    companion object {



        val instance: UserRepository by lazy {
            // Lazy means "When I need it" so here this block will be launch
            // the first time you need the instance,
            // then, the reference will be stored in the value `instance`
            UserRepositoryImpl(HttpClientManager.instance.createApi())
        }
    }
}