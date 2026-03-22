package com.example.tasques.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import com.example.tasques.data.entity.Tag
import com.example.tasques.databinding.ItemTagBinding

class TagAdapter(
    private val onItemClick: (Tag) -> Unit = {},
    private val onDeleteClick: (Tag) -> Unit = {}
) : ListAdapter<Tag, TagAdapter.TagViewHolder>(TagComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val binding = ItemTagBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TagViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class TagViewHolder(private val binding: ItemTagBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(getItem(position))
                }
            }

            binding.btnDelete.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onDeleteClick(getItem(position))
                }
            }
        }

        fun bind(tag: Tag) {
            binding.apply {
                tvTagName.text = tag.nom
            }
        }
    }

    class TagComparator : DiffUtil.ItemCallback<Tag>() {
        override fun areItemsTheSame(oldItem: Tag, newItem: Tag): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Tag, newItem: Tag): Boolean =
            oldItem == newItem
    }
}
