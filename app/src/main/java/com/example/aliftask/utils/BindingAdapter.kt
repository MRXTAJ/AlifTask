package com.example.aliftask.utils

import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aliftask.GuidesAdapter
import com.example.aliftask.R
import com.example.aliftask.database.Data
import com.example.aliftask.viewmodel.ApiStatus

@BindingAdapter("listOfGuides")
fun listGuides(recyclerView: RecyclerView, data: List<Data>?) {
    val adapter = recyclerView.adapter as GuidesAdapter?
    adapter?.submitList(data)
}

@BindingAdapter("imgIcon")
fun displayImage(img: ImageView?, url: String?) {
    try {
        img?.let {
            Glide.with(img.context!!).load(url)
                .into(it)
        }
    } catch (e: Exception) {
        throw e
    }
}

@BindingAdapter("guidesApiStatus")
fun bindStatus(statusImageView: ImageView, status: ApiStatus?) {
    when (status) {
        ApiStatus.LOADING -> {
            statusImageView.isVisible = true
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        ApiStatus.ERROR -> {
            statusImageView.isVisible = true
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        ApiStatus.SUCCESS -> {
            statusImageView.isVisible = false
        }
    }
}
