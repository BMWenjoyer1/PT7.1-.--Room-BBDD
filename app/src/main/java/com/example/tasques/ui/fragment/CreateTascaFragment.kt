package com.example.tasques.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasques.data.TasquesRepository
import com.example.tasques.databinding.FragmentCreateTascaBinding
import com.example.tasques.ui.adapter.SelectableTagAdapter
import com.example.tasques.ui.viewmodel.CreateTascaViewModel
import com.example.tasques.ui.viewmodel.CreateTascaViewModelFactory

class CreateTascaFragment : Fragment() {

    private lateinit var binding: FragmentCreateTascaBinding
    private lateinit var repository: TasquesRepository
    private lateinit var viewModel: CreateTascaViewModel
    private lateinit var tagAdapter: SelectableTagAdapter

    companion object {
        fun newInstance(repository: TasquesRepository): CreateTascaFragment {
            return CreateTascaFragment().apply {
                this.repository = repository
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateTascaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        val factory = CreateTascaViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(CreateTascaViewModel::class.java)

        // Setup tag adapter
        tagAdapter = SelectableTagAdapter()
        binding.rvTagsSelectable.apply {
            adapter = tagAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        // Observe tags
        viewModel.allTags.observe(viewLifecycleOwner) {
            tagAdapter.submitList(it)
        }

        // Setup buttons
        binding.btnSave.setOnClickListener {
            val titol = binding.etTitle.text.toString().trim()
            val descripcio = binding.etDescription.text.toString().trim()

            if (titol.isNotEmpty()) {
                val selectedTagIds = tagAdapter.getSelectedTagIds()
                viewModel.saveTasca(titol, descripcio, selectedTagIds)
                parentFragmentManager.popBackStack()
            } else {
                binding.etTitle.error = "El títol és obligatori"
            }
        }

        binding.btnCancel.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}
