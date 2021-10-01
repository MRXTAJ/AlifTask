package com.example.aliftask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aliftask.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    private lateinit var mBinding: FragmentDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        mBinding = FragmentDetailsBinding.inflate(inflater)

        return mBinding.root
    }

}