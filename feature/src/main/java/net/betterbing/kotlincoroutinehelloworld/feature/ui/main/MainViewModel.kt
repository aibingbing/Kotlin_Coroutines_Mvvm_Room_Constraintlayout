package net.betterbing.kotlincoroutinehelloworld.feature.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import net.betterbing.kotlincoroutinehelloworld.feature.MainApplication
import net.betterbing.kotlincoroutinehelloworld.feature.db.entity.Repos
import net.betterbing.kotlincoroutinehelloworld.feature.db.entity.User

class MainViewModel : ViewModel() {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val bgScope = CoroutineScope(Dispatchers.Default + viewModelJob)

    private var _users: LiveData<List<User>> = MainApplication.db.userDao().getAll()
    private var _reposes: LiveData<List<Repos>> = MainApplication.db.reposDao().getAll()

    fun showAllUsers(): LiveData<List<User>> {
        return _users
    }

    fun showAllUserRepos(): LiveData<List<Repos>> {
        return _reposes
    }

    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }
}