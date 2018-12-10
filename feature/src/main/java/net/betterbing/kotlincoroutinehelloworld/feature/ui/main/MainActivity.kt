package net.betterbing.kotlincoroutinehelloworld.feature.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.main_activity.*
import net.betterbing.kotlincoroutinehelloworld.feature.R

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        viewModel.showAllUsers().observe(this, Observer { users ->
            val stringBuilder = StringBuffer()
            users?.forEach { user ->
                stringBuilder.append("${user.id}    ${user.name}  \n")
            }
            userInfo.text = stringBuilder.toString()
        })

        viewModel.showAllUserRepos().observe(this, Observer {
            val stringBuilder = StringBuffer()
            it?.forEach { repos ->
                stringBuilder.append("${repos.id}    ${repos.name}  \n")
            }
            reposInfo.text = stringBuilder.toString()
        })
    }
}