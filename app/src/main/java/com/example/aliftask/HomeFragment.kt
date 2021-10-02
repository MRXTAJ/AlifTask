package com.example.aliftask

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.aliftask.database.AppDatabase
import com.example.aliftask.databinding.FragmentHomeBinding
import com.example.aliftask.viewmodel.MainViewModel


class HomeFragment : Fragment() {

    private lateinit var mBinding: FragmentHomeBinding
    private lateinit var mViewModel: MainViewModel
    private lateinit var mAdapter: GuidesAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        mBinding = FragmentHomeBinding.inflate(inflater)

        mBinding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val dataSource = AppDatabase.getInstance(application)
        val viewModelFactory = MainViewModel.ViewModelFactory(dataSource, application)

        mViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        mBinding.viewModel = mViewModel

        mAdapter = GuidesAdapter(GuidesAdapter.OnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                it.url))
        })

        mBinding.recyclerView.adapter = mAdapter

        mBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var scrollUp = -1
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    scrollUp = dy
                    Log.e("scroll", "$dy")
                } else {
                    scrollUp = -1
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && scrollUp != -1){
                    mViewModel.setData()
                }
            }
        })
        return mBinding.root
    }
}

