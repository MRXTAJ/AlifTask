package com.example.aliftask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.aliftask.databinding.FragmentHomeBinding
import com.example.aliftask.viewmodel.ApiStatus
import com.example.aliftask.viewmodel.MainViewModel

class HomeFragment : Fragment() {

    private lateinit var mBinding: FragmentHomeBinding
    private lateinit var mViewModel: MainViewModel
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: AdapterGridScrollProgress
    private val itemPerDisplay = 3

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        mBinding = FragmentHomeBinding.inflate(inflater)

        mViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        mBinding.recyclerView.layoutManager = StaggeredGridLayoutManager(2,
            StaggeredGridLayoutManager.VERTICAL)
        mBinding.recyclerView.setHasFixedSize(true)
        mRecyclerView = mBinding.recyclerView
        mViewModel.data.observe(viewLifecycleOwner, {
            mAdapter =
                AdapterGridScrollProgress(requireContext(),
                    itemPerDisplay,
                    it)
            mRecyclerView.adapter = mAdapter

            mAdapter.setOnLoadMoreListener(object : AdapterGridScrollProgress.OnLoadMoreListener {
                override fun onLoadMore(current_page: Int) {
                    loadNextData()
                }
            })
        })

        return mBinding.root

    }

    fun loadNextData() {
        mAdapter.setLoading()
        mViewModel.apiStatus.observe(viewLifecycleOwner, {
            mViewModel.apiStatus.observe(viewLifecycleOwner, { status ->
                when (status) {
                    ApiStatus.LOADING -> {
                        mAdapter.setLoading()
                    }
                    ApiStatus.SUCCESS -> {
                        mViewModel.data.value?.let { it1 -> mAdapter.insertData(it1) }
                    }

                    else -> {

                    }
                }
            })
        })

    }
}