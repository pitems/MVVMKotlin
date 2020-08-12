package com.example.mvvmsample.ui.home.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmsample.R
import com.example.mvvmsample.databinding.ProfileFragmentBinding

import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class profileFragment : Fragment() , KodeinAware{

    override val kodein by kodein()


    private lateinit var viewModel: ProfileViewModel
    private val factory:ProfileViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //The data for the binding was automatically created like inflater (because this is a fragment) where will be inflated
        // , the container and if we want it attached to the parent
        val binding : ProfileFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.profile_fragment,container,false)
        viewModel = ViewModelProvider(this,factory).get(ProfileViewModel::class.java)
        binding.viewmodel=viewModel
        binding.lifecycleOwner=this

        return binding.root
    }



}