package net.betterbing.kotlincoroutinehelloworld.feature.ui.login

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.*
import net.betterbing.kotlincoroutinehelloworld.feature.MainApplication
import net.betterbing.kotlincoroutinehelloworld.feature.db.entity.User
import net.betterbing.kotlincoroutinehelloworld.feature.network.RetrofitHelper
import net.betterbing.kotlincoroutinehelloworld.feature.network.model.GithubModel

class LoginViewModel : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val bgScope = CoroutineScope(Dispatchers.Default + viewModelJob)

    private val _toast = MutableLiveData<String>()
    private val _verifyUserSuccuess = MutableLiveData<Boolean>()
    private val _loadReposSuccess = MutableLiveData<Boolean>()
    private val githubModel = RetrofitHelper.create(
        net.betterbing.kotlincoroutinehelloworld.feature.MainApplication.BASE_URL,
        GithubModel::class.java
    )

    /**
     * Request a toast to display a string.
     */
    val toast: LiveData<String>
        get() = _toast

    val isVerifyUserSuccess: LiveData<Boolean>
        get() = _verifyUserSuccuess

    val loadReposSuccess: LiveData<Boolean>
        get() = _loadReposSuccess

    fun onMainViewClicked() {
        uiScope.launch {
            delay(1000)
            _toast.postValue("Hello, from threads!")
        }
    }

    /**
     * Called immediately after the UI shows the toast.
     */
    fun onToastShown() {
        _toast.value = null
    }

    fun verifyUser(userName: String) {
        uiScope.launch {
            val result = githubModel.getUserByUsername(userName).await()
            if (result.body() != null) {
                val user = result.body() ?: User()
                val task = bgScope.async {
                    MainApplication.db.userDao().addUser(user)
                }
                task.await()
                _verifyUserSuccuess.value = true
            } else {
                _toast.value = "verify user failed"
            }
        }
    }

    fun loadUserRepos(userName: String) {
        uiScope.launch {
            val result = githubModel.getUserRepos(userName).await()
            if (result.isSuccessful) {
                val saveTask = bgScope.async {
                    result.body()?.run {
                        forEach {
                            MainApplication.db.reposDao().addRepos(it)
                        }
                    }
                }
                saveTask.await()
                _loadReposSuccess.value = true
            } else {
                _toast.value = "getUserRepos failed"
            }
        }
    }

    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }
}
