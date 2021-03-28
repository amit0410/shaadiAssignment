package com.example.shaadi.feature.ui.main.shaadi

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shaadi.core.di.Injectable
import com.example.shaadi.databinding.MainFragmentBinding
import com.example.shaadi.feature.MatchStatus
import com.example.shaadi.feature.contract.UserModel
import com.example.shaadi.feature.ui.main.shaadi.adapters.ShaadiAdapter
import javax.inject.Inject

class MainFragment : Fragment(),Injectable,ShaadiAdapter.Interaction {

    companion object {
        fun newInstance() = MainFragment()
    }

    lateinit var shaadiAdapter: ShaadiAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MainViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = MainFragmentBinding.inflate(inflater,container,false)
        binding.init()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getUsers().observe(viewLifecycleOwner, Observer(:: renderUsers))
        viewModel.fetchData(10)
    }

    private fun MainFragmentBinding.init(){
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            shaadiAdapter = ShaadiAdapter(this@MainFragment)
            adapter = shaadiAdapter
        }
    }

    private fun renderUsers(response: List<UserModel>?) {
        response?.let {
            shaadiAdapter.submitList(it)
        }

    }

    override fun onAccept(position: Int, item: UserModel) {
       viewModel.updateUser(item,MatchStatus.Accepted.toString())
    }

    override fun onReject(position: Int, item: UserModel) {
        viewModel.updateUser(item,MatchStatus.Declined.toString())
    }

}