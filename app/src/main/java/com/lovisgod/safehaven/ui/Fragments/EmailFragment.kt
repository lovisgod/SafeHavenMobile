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
import com.lovisgod.safehaven.databinding.FragmentEmailBinding
import com.lovisgod.safehaven.databinding.FragmentFullNameBinding
import com.lovisgod.safehaven.viewmodel.AuthViewModel

/**
 * A simple [Fragment] subclass.
 */
class EmailFragment : Fragment() {
    private lateinit var binding: FragmentEmailBinding
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
        // Inflate the layout for this fragment
        navController = Navigation.findNavController(this.requireActivity(), R.id.auth_nav_host_fragment)
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_email, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        binding.loginBtn.setOnClickListener {
            viewModel.saveEmail(binding.email.text.toString())
            navController.navigate(R.id.action_emailFragment_to_passwordFragment)
        }

        return binding.root
    }

}
