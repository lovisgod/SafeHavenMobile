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
import com.lovisgod.safehaven.databinding.FragmentSafetyTipsBinding
import com.lovisgod.safehaven.model.Contact
import com.lovisgod.safehaven.model.SafetyTip
import com.lovisgod.safehaven.ui.Adapters.SafetyTipsListAdapter
import com.lovisgod.safehaven.viewmodel.AppViewModel

/**
 * A simple [Fragment] subclass.
 */
class SafetyTipsFragment : Fragment() {
    private lateinit var binding: FragmentSafetyTipsBinding
    private lateinit var navController: NavController
    private lateinit var adapter : SafetyTipsListAdapter

    // this is contact list that will be removed later
    private var tipList: ArrayList<SafetyTip> = ArrayList()
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
        navController = Navigation.findNavController(this.requireActivity(),R.id.app_nav_host_fragment)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_safety_tips, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        adapter = SafetyTipsListAdapter(navController)
        binding.safetyTipsList.layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.VERTICAL,false)
        binding.safetyTipsList.adapter = adapter

         val tip1= SafetyTip(
             title = "This is tip here",
             category = "Business",
             time = "2hrs ago",
             body = getString(R.string.body_sample)
         )
        val tip2= SafetyTip(
            title = "This is tip here",
            category = "Safety",
            time = "2hrs ago",
            body = getString(R.string.body_sample)
        )
        val tip3= SafetyTip(
            title = "This is tip here",
            category = "Business",
            time = "3hrs ago",
            body = getString(R.string.body_sample)
        )
        tipList.add(tip1)
        tipList.add(tip2)
        tipList.add(tip3)

        adapter.setAdvertList(tipList)
        return binding.root
    }

}
