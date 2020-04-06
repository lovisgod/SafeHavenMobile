package com.lovisgod.safehaven.ui.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.lovisgod.safehaven.R
import com.lovisgod.safehaven.databinding.FragmentPhoneBinding
import com.lovisgod.safehaven.databinding.FragmentVerifyBinding
import com.lovisgod.safehaven.viewModel.AuthViewModel

/**
 * A simple [Fragment] subclass.
 */
class VerifyFragment : Fragment() {
    private lateinit var binding: FragmentVerifyBinding
    private lateinit var navController: NavController
    private val viewModel: AuthViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, AuthViewModel.Factory(activity.application))
            .get(AuthViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        navController = Navigation.findNavController(this.requireActivity(), R.id.auth_nav_host_fragment)
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_verify, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        binding.loginBtn.setOnClickListener {
          navController.navigate(R.id.action_verifyFragment_to_loginFragment)
        }

        return binding.root    }

}
