package com.lovisgod.safehaven.ui.Fragments

import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.loadingview.LoadingDialog

import com.lovisgod.safehaven.R
import com.lovisgod.safehaven.databinding.FragmentHospFireBinding
import com.lovisgod.safehaven.model.Business
import com.lovisgod.safehaven.model.Contact
import com.lovisgod.safehaven.ui.Adapters.HospFireAdapter
import com.lovisgod.safehaven.viewmodel.AppViewModel
import com.robin.locationgetter.EasyLocation

/**
 * A simple [Fragment] subclass.
 */
class HospFireFragment : Fragment() {
    private lateinit var binding: FragmentHospFireBinding
    private lateinit var navController: NavController
    private lateinit var adapter : HospFireAdapter

    // this is contact list that will be removed later
    private var sampleContactList: ArrayList<Contact> = ArrayList()
    private var businesses: ArrayList<Business> = ArrayList()
    private val viewModel: AppViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(activity, AppViewModel.Factory(activity.application))
            .get(AppViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val arguements = arguments
        // Inflate the layout for this fragment
        navController = Navigation.findNavController(this.requireActivity(), R.id.app_nav_host_fragment)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hosp_fire, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        adapter = HospFireAdapter(viewModel, viewLifecycleOwner, this.requireActivity())
        binding.contactList.layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.VERTICAL,false)
        binding.contactList.adapter = adapter
        if (arguements?.getString("to_contact", "") != null) {
            binding.forEmValue.text = arguements.getString("to_contact", "")

//            if (arguements.getString("to_contact", "") == "Ambulance") {
//                viewModel.searchPlaces(type = "hospital")
//            }
//
//            if (arguements.getString("to_contact", "") == "Fire Service") {
//                viewModel.searchPlaces(type = "hospital")
//            }
        }


//        // create sample contact items
//        val contact1 = Contact(
//            name = "Adego Hospital",
//            phone_number = "09098765432",
//            address = resources.getString(R.string.lawal_st_ne),
//            status = "close"
//        )
//        val contact2 = Contact(
//            name = "Adega Hospital",
//            phone_number = "09098765433",
//            address = resources.getString(R.string.lawal_st_ne),
//            status = "open"
//        )
//        sampleContactList.add(contact1)
//        sampleContactList.add(contact2)

        EasyLocation(this.requireActivity(), object: EasyLocation.EasyLocationCallBack{
            override fun permissionDenied() {

                Log.i("Location", "permission  denied")


            }

            override fun locationSettingFailed() {

                Log.i("Location", "setting failed")


            }

            override fun getLocation(location: Location) {
                val loc = "${location.latitude},${location.longitude}"
                if (arguements?.getString("to_contact", "") != null) {

                    if (arguements.getString("to_contact", "") == "Ambulance") {
                        viewModel.searchPlaces(type = "hospital", location = loc)
                    }

                    if (arguements.getString("to_contact", "") == "Fire Service") {
                        viewModel.searchPlaces(type = "hospital", location = loc)
                    }
                }
            }
        })

        val dialog = LoadingDialog.get(this.requireActivity()).show()
        viewModel._placesAround.observe(viewLifecycleOwner, Observer {
            if( it.size == 0){

            } else {
                if (businesses.size == 0) {
                    businesses = it
                    adapter.setAdvertList(it)
                    dialog.hide()
                }
            }
        })
//        adapter.setAdvertList(sampleContactList)
        return binding.root
    }

}
