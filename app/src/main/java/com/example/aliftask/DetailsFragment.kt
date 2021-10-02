package com.example.aliftask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.aliftask.databinding.FragmentDetailsBinding
import com.example.aliftask.network.GuidesApi.BASE_URL

class DetailsFragment : Fragment() {

    private lateinit var mBinding: FragmentDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        mBinding = FragmentDetailsBinding.inflate(inflater)

        setHasOptionsMenu(true)
        mBinding.webView.webViewClient = WebViewClient()

        val url = DetailsFragmentArgs.fromBundle(requireArguments()).url

        // this will load the url of the website
        mBinding.webView.loadUrl(BASE_URL + url)

        mBinding.webView.settings.javaScriptEnabled = true

        // if you want to enable zoom feature
        mBinding.webView.settings.setSupportZoom(true)


        return mBinding.root
    }

}