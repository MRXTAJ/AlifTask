package com.example.aliftask

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aliftask.database.Data
import com.example.aliftask.databinding.ItemListBinding

class GuidesAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Data, GuidesAdapter.GuidesPropertyViewHolder>(DiffCallback) {


    class GuidesPropertyViewHolder(private var binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(guides: Data) {
            binding.properties = guides
            binding.executePendingBindings()
        }
    }

    class OnClickListener(val clickListener: (guide: Data) -> Unit) {
        fun onClick(guide: Data) = clickListener(guide)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.name == newItem.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuidesPropertyViewHolder {
        return GuidesPropertyViewHolder(ItemListBinding.inflate(LayoutInflater.from(
            parent.context)))
    }

    override fun onBindViewHolder(holder: GuidesPropertyViewHolder, position: Int) {
        val property = getItem(position)

        holder.itemView.setOnClickListener {
            onClickListener.onClick(property)
        }
        holder.bind(property)
    }
}