package com.example.aliftask

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.aliftask.database.Data
import com.example.aliftask.databinding.FragmentHomeBinding
import com.example.aliftask.viewmodel.ApiStatus
import com.example.aliftask.viewmodel.MainViewModel

class HomeFragment : Fragment() {

    private lateinit var mBinding: FragmentHomeBinding
    private lateinit var mViewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        mBinding = FragmentHomeBinding.inflate(inflater)

        mBinding.lifecycleOwner = this

        mViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mBinding.viewModel = mViewModel

        mBinding.recyclerView.adapter = GuidesAdapter(GuidesAdapter.OnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                it.url))
        })
        return mBinding.root
    }
}

