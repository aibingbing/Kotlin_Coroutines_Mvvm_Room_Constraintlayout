package net.betterbing.kotlincoroutinehelloworld.feature.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.login_fragment.*
import net.betterbing.kotlincoroutinehelloworld.feature.R
import net.betterbing.kotlincoroutinehelloworld.feature.ui.main.MainActivity

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        icon.setOnClickListener {
            viewModel.onMainViewClicked()
        }

        verifyUserBtn.setOnClickListener {
            viewModel.verifyUser(userNameEditText.text.toString())
        }
        loadReposBtn.setOnClickListener {
            viewModel.loadUserRepos(userNameEditText.text.toString())
        }
        viewModel.toast.observe(this, Observer {
            it?.let {
                Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
                viewModel.onToastShown()
            }
        })
        viewModel.isVerifyUserSuccess.observe(this, Observer {
            Toast.makeText(activity, "Verify User Success : ${it ?: false}", Toast.LENGTH_LONG).show()
        })
        viewModel.loadReposSuccess.observe(this, Observer {
            val intent = Intent(activity, MainActivity::class.java)
            activity?.startActivity(intent)
            activity?.finish()
        })
    }

}
