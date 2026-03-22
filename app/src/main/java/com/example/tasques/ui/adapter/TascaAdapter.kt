package com.example.tasques.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import com.example.tasques.data.entity.TascaWithTags
import com.example.tasques.databinding.ItemTascaBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TascaAdapter(
    private val onItemClick: (TascaWithTags) -> Unit = {},
    private val onDeleteClick: (TascaWithTags) -> Unit = {},
    private val onStatusChange: (TascaWithTags, String) -> Unit = { _, _ -> }
) : ListAdapter<TascaWithTags, TascaAdapter.TascaViewHolder>(TascaComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TascaViewHolder {
        val binding = ItemTascaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TascaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TascaViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class TascaViewHolder(private val binding: ItemTascaBinding) :
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

            binding.spinnerStatus.setSelection(getStatusIndex(binding.spinnerStatus.selectedItem.toString()))
        }

        fun bind(tasca: TascaWithTags) {
            binding.apply {
                tvTitle.text = tasca.tasca.titol
                tvDescription.text = tasca.tasca.descripcio

                val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                tvDate.text = dateFormat.format(Date(tasca.tasca.dataCreacio))

                // Set status spinner
                val statuses = listOf("pendent", "en_proces", "completada")
                val statusIndex = statuses.indexOf(tasca.tasca.estat)
                spinnerStatus.setSelection(if (statusIndex >= 0) statusIndex else 0)

                spinnerStatus.setOnItemSelectedListener(object : android.widget.AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: android.widget.AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                        if (position != statuses.indexOf(tasca.tasca.estat)) {
                            onStatusChange(tasca, statuses[position])
                        }
                    }

                    override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {}
                })

                // Display tags
                if (tasca.tags.isNotEmpty()) {
                    tvTags.text = tasca.tags.joinToString(", ") { it.nom }
                    tvTags.visibility = android.view.View.VISIBLE
                } else {
                    tvTags.visibility = android.view.View.GONE
                }
            }
        }

        private fun getStatusIndex(status: String): Int {
            return when (status) {
                "en_proces" -> 1
                "completada" -> 2
                else -> 0
            }
        }
    }

    class TascaComparator : DiffUtil.ItemCallback<TascaWithTags>() {
        override fun areItemsTheSame(
            oldItem: TascaWithTags,
            newItem: TascaWithTags
        ): Boolean = oldItem.tasca.id == newItem.tasca.id

        override fun areContentsTheSame(
            oldItem: TascaWithTags,
            newItem: TascaWithTags
        ): Boolean = oldItem == newItem
    }
}
