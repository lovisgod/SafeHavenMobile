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
import com.lovisgod.safehaven.databinding.FragmentDirectoryCategoryBinding
import com.lovisgod.safehaven.ui.Adapters.DirectoryCategoryListAdapter
import com.lovisgod.safehaven.viewmodel.AppViewModel

/**
 * A simple [Fragment] subclass.
 */
class DirectoryCategoryFragment : Fragment() {
    private lateinit var binding: FragmentDirectoryCategoryBinding
    private lateinit var navController: NavController
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_directory_category, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        binding.directoryCategories.adapter = DirectoryCategoryListAdapter(this.requireContext())
        return binding.root
    }

}
