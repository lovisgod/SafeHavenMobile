package com.lovisgod.safehaven.ui.Fragments

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.lovisgod.safehaven.R
import com.lovisgod.safehaven.Util.Dialog
import com.lovisgod.safehaven.viewmodel.AuthViewModel
import com.lovisgod.safehaven.databinding.FragmentLoginBinding
import com.lovisgod.safehaven.model.AppEvent
import com.lovisgod.safehaven.ui.Activity.LandingActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var navController: NavController
    private val viewModel: AuthViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(activity, AuthViewModel.Factory(activity.application))
            .get(AuthViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = Navigation.findNavController(this.requireActivity(), R.id.auth_nav_host_fragment)
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_login, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        binding.loginBtn.setOnClickListener {
           binding.progress.visibility =View.VISIBLE
            viewModel.login(email = binding.email.text.toString(),password = binding.password.text.toString())
        }

        binding.signup.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_fullnameFragment)
        }
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onAppEvent(event: AppEvent) {
        binding.progress.visibility = View.INVISIBLE
        when(event.event) {
            "success" -> {
                Dialog().makeSnack(binding.loginBtn, event.message, this.requireContext())
                startActivity(Intent(this.requireActivity(), LandingActivity::class.java))
            }

            "error" -> {
                // display message
                Dialog().makeSnack(binding.loginBtn, event.message, this.requireContext())
            }
        }
    }



    override fun onStart() {
        super.onStart()
        // registers the event listener
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        // unregister the event listener
        EventBus.getDefault().unregister(this)
    }


}
