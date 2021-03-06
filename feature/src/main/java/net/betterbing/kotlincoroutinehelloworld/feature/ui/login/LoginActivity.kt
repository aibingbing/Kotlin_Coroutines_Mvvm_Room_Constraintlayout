package net.betterbing.kotlincoroutinehelloworld.feature.ui.login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import net.betterbing.kotlincoroutinehelloworld.feature.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LoginFragment.newInstance())
                .commitNow()
        }
    }

}
