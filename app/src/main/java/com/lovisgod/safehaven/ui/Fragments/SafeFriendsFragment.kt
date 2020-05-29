package com.lovisgod.safehaven.ui.Fragments

import android.app.AlertDialog
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
import androidx.recyclerview.widget.LinearLayoutManager

import com.lovisgod.safehaven.R
import com.lovisgod.safehaven.Util.Dialog
import com.lovisgod.safehaven.databinding.FragmentHospFireBinding
import com.lovisgod.safehaven.databinding.FragmentSafeFriendsBinding
import com.lovisgod.safehaven.databinding.FragmentSafetyTipsBinding
import com.lovisgod.safehaven.model.AppEvent
import com.lovisgod.safehaven.model.ClosedMDialog
import com.lovisgod.safehaven.model.Contact
import com.lovisgod.safehaven.ui.Activity.LandingActivity
import com.lovisgod.safehaven.ui.Adapters.SafeFriendsListAdapter
import com.lovisgod.safehaven.viewmodel.AppViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * A simple [Fragment] subclass.
 */
class SafeFriendsFragment : Fragment() {
    private lateinit var binding: FragmentSafeFriendsBinding
    private lateinit var adapter: SafeFriendsListAdapter
    private lateinit var navController: NavController
    private var sampleFriendList: ArrayList<Contact> = ArrayList()
    private var dailog = Dialog()
    private lateinit var showedDialog: AlertDialog

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
        // Inflate the layout for this fragment
        navController = Navigation.findNavController(this.requireActivity(), R.id.app_nav_host_fragment)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_safe_friends, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        adapter = SafeFriendsListAdapter()
        binding.friendsList.layoutManager = LinearLayoutManager(
            this.requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.friendsList.adapter = adapter

        // create sample contact items
        val contact1 = Contact(
            name = "Friend One",
            phone_number = "09098765432"
        )
        val contact2 = Contact(
            name = "Friend Two",
            phone_number = "09098765433"
        )

        val contact3 = Contact(
            name = "Friend Three",
            phone_number = "09098765433"
        )
        sampleFriendList.add(contact1)
        sampleFriendList.add(contact2)
        sampleFriendList.add(contact3)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            showedDialog = dailog.displayAddContactDialog(this.requireContext())!!
        }


        adapter.setFriendList(sampleFriendList)

        binding.addFriend.setOnClickListener {
           showedDialog.show()
        }
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onClosedMDialog(event: ClosedMDialog) {
        when(event.event) {
            "close" -> {
               showedDialog.cancel()
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
