package com.lovisgod.safehaven.ui.Fragments

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.lovisgod.safehaven.R
import com.lovisgod.safehaven.databinding.FragmentLandingBinding
import com.lovisgod.safehaven.viewmodel.AppViewModel

/**
 * A simple [Fragment] subclass.
 */
class LandingFragment : Fragment() {
    private lateinit var binding: FragmentLandingBinding
    private lateinit var navController: NavController
    private val viewModel: AppViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(activity, AppViewModel.Factory(activity.application))
            .get(AppViewModel::class.java)
    }

    companion object {

        private const val LOCATION_PERMISSION_REQUEST_CODE = 1

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        navController = Navigation.findNavController(this.requireActivity(), R.id.app_nav_host_fragment)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_landing, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        checkLocationPermision()

        var bundle = Bundle()
        binding.hospitalCard.setOnClickListener {
          bundle.putString("to_contact", "Ambulance")
          navController.navigate(R.id.action_landingFragment_to_contactFragment, bundle)
        }
        binding.fireStationCard.setOnClickListener {
            bundle.putString("to_contact", "Fire Service")
            navController.navigate(R.id.action_landingFragment_to_contactFragment, bundle)
        }

        binding.directoryCard.setOnClickListener {
            navController.navigate(R.id.action_landingFragment_to_directoryFragment)
        }

        binding.safetyTipsCard.setOnClickListener {
            navController.navigate(R.id.action_landingFragment_to_safetyTipsFragment)
        }

        binding.safeFriends.setOnClickListener {
            navController.navigate(R.id.action_landingFragment_to_safeFriendsFragment)
        }

        showDetails()
        return binding.root
    }

    fun checkLocationPermision() {
        if (ActivityCompat.checkSelfPermission(
                this.requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this.requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

            }
        }
    }

    fun showDetails () {
       viewModel._name.observe(viewLifecycleOwner, Observer {
           binding.profileName.text = it
       })
        viewModel._email.observe(viewLifecycleOwner, Observer {
            println(it)
            binding.profileEmail.text = it
        })
       viewModel._phoneNumber.observe(viewLifecycleOwner, Observer {
           println(it)
           binding.profilePhone.text = it
       })
    }
}
