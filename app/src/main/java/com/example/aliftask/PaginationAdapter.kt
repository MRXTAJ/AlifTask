package com.example.aliftask

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.aliftask.database.Data
import java.util.*

class PaginationAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var guidesList: MutableList<Data>? = LinkedList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            ITEM -> {
                val viewItem: View = inflater.inflate(R.layout.item_list, parent, false)
                viewHolder = GuideViewHolder(viewItem)
            }
            LOADING -> {
                val viewLoading: View = inflater.inflate(R.layout.item_progress, parent, false)
                viewHolder = LoadingViewHolder(viewLoading)
            }
        }
        return viewHolder!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val guides = guidesList!![position]
        when (getItemViewType(position)) {
            ITEM -> {
                val movieViewHolder = holder as GuideViewHolder
                movieViewHolder.name.text = guides.name
                Glide.with(context).load(guides.icon)
                    .apply(RequestOptions.centerCropTransform())
                    .into(movieViewHolder.logo)
            }
            LOADING -> {
                val loadingViewHolder = holder as LoadingViewHolder
                loadingViewHolder.progressBar.visibility = View.VISIBLE
            }
        }
    }

    override fun getItemCount(): Int {
        return if (guidesList == null) 0 else guidesList!!.size
    }

    class GuideViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.movie_title)
        val logo: ImageView = itemView.findViewById(R.id.movie_poster) as ImageView

    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar)

    }

    companion object {
        private const val LOADING = 0
        private const val ITEM = 1
    }

}