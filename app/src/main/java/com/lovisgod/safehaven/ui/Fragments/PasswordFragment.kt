package com.lovisgod.safehaven.ui.Fragments

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
import com.lovisgod.safehaven.databinding.FragmentPasswordBinding
import com.lovisgod.safehaven.model.AppEvent
import com.lovisgod.safehaven.viewmodel.AuthViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * A simple [Fragment] subclass.
 */
class PasswordFragment : Fragment() {
    private lateinit var binding: FragmentPasswordBinding
    private lateinit var navController: NavController
    private val viewModel: AuthViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(activity, AuthViewModel.Factory(activity.application))
            .get(AuthViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        navController = Navigation.findNavController(this.requireActivity(), R.id.auth_nav_host_fragment)
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_password, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        binding.loginBtn.setOnClickListener {
          binding.progress.visibility = View.VISIBLE
          if ( binding.password.text.toString() != binding.passwordConfirm.text.toString()) {
              Dialog().makeSnack(binding.loginBtn, "Please check your password", this.requireContext())
              binding.progress.visibility = View.GONE
          } else {
              viewModel.fireBaseSignup(binding.password.text.toString())
          }
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
                navController.navigate(R.id.action_passwordFragment_to_loginFragment)
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
