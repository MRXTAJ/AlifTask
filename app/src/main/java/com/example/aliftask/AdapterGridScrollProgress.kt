package com.example.aliftask

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.aliftask.database.Data
import com.example.aliftask.utils.displayImageOriginal
import kotlin.collections.ArrayList

class AdapterGridScrollProgress(
    context: Context,
    item_per_display: Int,
    items: MutableList<Data>?,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private val VIEW_ITEM = 1
        private val VIEW_PROGRESS = 0
    }

    private var item_per_display = 0


    private var items: MutableList<Data>?
    private var loading = false
    private var onLoadMoreListener: OnLoadMoreListener? = null
    private val ctx: Context
    private var mOnItemClickListener: OnItemClickListener? = null

    init {
        this.items = items
        this.item_per_display = item_per_display
        ctx = context
    }

    interface OnItemClickListener {
        fun onItemClick(view: View?, obj: Data?, position: Int)
    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = mItemClickListener
    }

    class OriginalViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var image: ImageView = v.findViewById<View>(R.id.movie_poster) as ImageView

    }

    class ProgressViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var progressBar: ProgressBar = v.findViewById<View>(R.id.progress_bar) as ProgressBar

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_ITEM) {
            val v: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list, parent, false)
            OriginalViewHolder(v)
        } else {
            val v: View =
                LayoutInflater.from(parent.context).inflate(R.layout.item_progress, parent, false)
            ProgressViewHolder(v)
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val s: Data = items!![position]
        if (holder is OriginalViewHolder) {
            displayImageOriginal(ctx, holder.image, s.icon)
//            holder.layout.setOnClickListener(View.OnClickListener { view ->
//                if (mOnItemClickListener == null) return@OnClickListener
//                mOnItemClickListener!!.onItemClick(view, s, position)
//            })
        } else {
            (holder as ProgressViewHolder).progressBar.isIndeterminate =
                true
        }
        if (s.progress) {
            val layoutParams =
                holder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
            layoutParams.isFullSpan = true
        } else {
            val layoutParams =
                holder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
            layoutParams.isFullSpan = false
        }
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (items!![position].progress) VIEW_PROGRESS else VIEW_ITEM
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        lastItemViewDetector(recyclerView)
        super.onAttachedToRecyclerView(recyclerView)
    }

    fun insertData(items: List<Data>) {
        setLoaded()
        val positionStart = itemCount
        val itemCount = items.size
        this.items!!.addAll(items)
        notifyItemRangeInserted(positionStart, itemCount)
    }

    fun setLoaded() {
        loading = false
        for (i in 0 until itemCount) {
            if (items!![i].progress) {
                items!!.removeAt(i)
                notifyItemRemoved(i)
            }
        }
    }

    fun setLoading() {
        if (itemCount != 0) {
            items!!.add(Data(true))
            notifyItemInserted(itemCount - 1)
            loading = true
        }
    }

    fun resetListData() {
        items = ArrayList<Data>()
        notifyDataSetChanged()
    }

    fun setOnLoadMoreListener(onLoadMoreListener: OnLoadMoreListener?) {
        this.onLoadMoreListener = onLoadMoreListener
    }

    private fun lastItemViewDetector(recyclerView: RecyclerView) {
        if (recyclerView.layoutManager is StaggeredGridLayoutManager) {
            val layoutManager = recyclerView.layoutManager as StaggeredGridLayoutManager?
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val lastPos =
                        getLastVisibleItem(layoutManager!!.findLastVisibleItemPositions(null))
                    if (!loading && lastPos == itemCount - 1 && onLoadMoreListener != null) {
                        val current_page = itemCount / item_per_display
                        onLoadMoreListener!!.onLoadMore(current_page)
                        loading = true
                    }
                }
            })
        }
    }

    interface OnLoadMoreListener {
        fun onLoadMore(current_page: Int)
    }

    private fun getLastVisibleItem(into: IntArray): Int {
        var last_idx = into[0]
        for (i in into) {
            if (last_idx < i) last_idx = i
        }
        return last_idx
    }

}