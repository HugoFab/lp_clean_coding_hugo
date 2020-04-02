package fr.hugo.template.data.networking.datasource

import android.util.Log
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import fr.hugo.template.data.model.User
import fr.hugo.template.data.networking.api.GithubApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserDataSource private constructor(
    private val api: GithubApi,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, User>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, User>
    ) {
        scope.launch(Dispatchers.IO) {
            try {
                val response = api.getAllUsers(
                    accessToken = ACCESS_TOKEN,
                    perPage = USER_PER_PAGE,
                    since = FIRST_KEY
                ).run {
                    Log.d("ALED",this.body().toString())
                    if (this.isSuccessful) this.body()
                        ?: throw IllegalStateException("Body is null")
                    else throw IllegalStateException("Response is not successful : code = ${this.code()}")
                }
                if (params.placeholdersEnabled) callback.onResult(
                    response,
                    0,
                    MAX_USER,
                    null,
                    response.last().id
                ) else {
                    Log.d("ALED my body",response.toString())
                    callback.onResult(
                        response,
                        null,
                        response.last().id
                    )
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {
        scope.launch(Dispatchers.IO) {
            try {
                val response = api.getAllUsers(
                    accessToken = ACCESS_TOKEN,
                    perPage = USER_PER_PAGE,
                    since = params.key
                ).run {
                    if (this.isSuccessful) this.body()
                        ?: throw IllegalStateException("Body is null")
                    else throw IllegalStateException("Response is not successful : code = ${this.code()}")
                }
                callback.onResult(
                    response,
                    response.last().id
                )
            } catch (e: Exception) {
                Log.e(TAG, "loadInitial: ", e)
            }
        }
    }

    // This method will not be used in this app
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, User>) = Unit

    class Factory(
        private val api: GithubApi,
        private val scope: CoroutineScope
    ) : DataSource.Factory<Int, User>() {
        override fun create(): DataSource<Int, User> =
            UserDataSource(
                api, scope
            )
    }

    companion object {
        private const val TAG: String = "UserDataSource"
        private const val FIRST_KEY = 0
        private const val ACCESS_TOKEN : String ="a57aa7a5d6a8b277253f0ff2bade02b2c61a9b99"
        private const val USER_PER_PAGE = 20
        private const val MAX_USER = 999999
    }

}