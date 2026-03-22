package com.example.tasques.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasques.data.TasquesRepository
import com.example.tasques.databinding.FragmentTagsBinding
import com.example.tasques.ui.adapter.TagAdapter
import com.example.tasques.ui.viewmodel.TagViewModel
import com.example.tasques.ui.viewmodel.TagViewModelFactory

class TagsFragment : Fragment() {

    private lateinit var binding: FragmentTagsBinding
    private lateinit var repository: TasquesRepository
    private lateinit var viewModel: TagViewModel
    private lateinit var tagAdapter: TagAdapter

    companion object {
        fun newInstance(repository: TasquesRepository): TagsFragment {
            return TagsFragment().apply {
                this.repository = repository
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTagsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        val factory = TagViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(TagViewModel::class.java)

        // Setup adapter
        tagAdapter = TagAdapter(
            onDeleteClick = { viewModel.deleteTag(it) }
        )

        binding.rvTags.apply {
            adapter = tagAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        // Observe tags
        viewModel.allTags.observe(viewLifecycleOwner) {
            tagAdapter.submitList(it)
        }

        // Setup add button
        binding.btnAddTag.setOnClickListener {
            val tagName = binding.etNewTag.text.toString().trim()
            if (tagName.isNotEmpty()) {
                viewModel.addTag(tagName)
                binding.etNewTag.text.clear()
            } else {
                binding.etNewTag.error = "El nom del tag és obligatori"
            }
        }
    }
}
