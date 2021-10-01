package com.example.aliftask.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

//@BindingAdapter("listOfOrders")
//fun listOrders(recyclerView: RecyclerView, data: List<com.example.foodbox.model.order.Data>?) {
//    val adapter = recyclerView.adapter as OrdersAdapter?
//    adapter?.submitList(data)
//}
fun displayImageOriginal(ctx: Context?, img: ImageView?, url: String?) {
    try {
        img?.let {
            Glide.with(ctx!!).load(url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(it)
        }
    } catch (e: Exception) {
    }
}
