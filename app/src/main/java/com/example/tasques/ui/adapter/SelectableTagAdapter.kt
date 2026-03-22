package com.example.tasques.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import com.example.tasques.data.entity.Tag
import com.example.tasques.databinding.ItemTagSelectableBinding

class SelectableTagAdapter(
    private val selectedTagIds: MutableSet<Int> = mutableSetOf(),
    private val onSelectionChanged: (Int, Boolean) -> Unit = { _, _ -> }
) : ListAdapter<Tag, SelectableTagAdapter.SelectableTagViewHolder>(TagComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectableTagViewHolder {
        val binding = ItemTagSelectableBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SelectableTagViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectableTagViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class SelectableTagViewHolder(private val binding: ItemTagSelectableBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tag: Tag) {
            binding.apply {
                cbTag.text = tag.nom
                cbTag.isChecked = selectedTagIds.contains(tag.id)

                cbTag.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        selectedTagIds.add(tag.id)
                        onSelectionChanged(tag.id, true)
                    } else {
                        selectedTagIds.remove(tag.id)
                        onSelectionChanged(tag.id, false)
                    }
                }
            }
        }
    }

    fun getSelectedTagIds(): List<Int> = selectedTagIds.toList()

    class TagComparator : DiffUtil.ItemCallback<Tag>() {
        override fun areItemsTheSame(oldItem: Tag, newItem: Tag): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Tag, newItem: Tag): Boolean =
            oldItem == newItem
    }
}
