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
import androidx.recyclerview.widget.LinearLayoutManager

import com.lovisgod.safehaven.R
import com.lovisgod.safehaven.databinding.FragmentDirectoryListBinding
import com.lovisgod.safehaven.model.Contact
import com.lovisgod.safehaven.ui.Adapters.DirectoryListAdapter
import com.lovisgod.safehaven.viewmodel.AppViewModel

/**
 * A simple [Fragment] subclass.
 */
class DirectoryListFragment : Fragment() {
    private lateinit var binding : FragmentDirectoryListBinding
    private lateinit var navController: NavController
    private var adapter = DirectoryListAdapter()

    // this is contact list that will be removed later
    private var sampleContactList: ArrayList<Contact> = ArrayList()
    private val viewModel: AppViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, AppViewModel.Factory(activity.application))
            .get(AppViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        navController = Navigation.findNavController(this.requireActivity(), R.id.app_nav_host_fragment)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_directory_list,container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        binding.contactList.layoutManager = LinearLayoutManager(
            this.requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.contactList.adapter = adapter


        // create sample contact items
        val contact1 = Contact(
            name = "Adego Hospital",
            phone_number = "09098765432",
            address = resources.getString(R.string.lawal_st_ne),
            status = "close"
        )
        val contact2 = Contact(
            name = "Adega Hospital",
            phone_number = "09098765433",
            address = resources.getString(R.string.lawal_st_ne),
            status = "open"
        )

        val contact3 = Contact(
            name = "Adega Hospital",
            phone_number = "09098765433",
            address = resources.getString(R.string.lawal_st_ne),
            status = "open"
        )
        sampleContactList.add(contact1)
        sampleContactList.add(contact2)
        sampleContactList.add(contact3)
        adapter.setAdvertList(sampleContactList)

        return binding.root
    }

}
