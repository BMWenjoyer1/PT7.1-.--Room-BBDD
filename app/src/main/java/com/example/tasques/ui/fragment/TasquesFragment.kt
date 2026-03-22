package com.example.tasques.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasques.data.TasquesRepository
import com.example.tasques.databinding.FragmentTasquesBinding
import com.example.tasques.ui.adapter.TascaAdapter
import com.example.tasques.ui.viewmodel.TascaListViewModel
import com.example.tasques.ui.viewmodel.TascaListViewModelFactory

class TasquesFragment : Fragment() {

    private lateinit var binding: FragmentTasquesBinding
    private lateinit var repository: TasquesRepository
    private lateinit var viewModel: TascaListViewModel
    private lateinit var tasquAdapter: TascaAdapter

    companion object {
        private const val REPO_KEY = "repository"

        fun newInstance(repository: TasquesRepository): TasquesFragment {
            return TasquesFragment().apply {
                this.repository = repository
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTasquesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        val factory = TascaListViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(TascaListViewModel::class.java)

        // Setup adapter
        tasquAdapter = TascaAdapter(
            onItemClick = { },
            onDeleteClick = { viewModel.deleteTasca(it.tasca) },
            onStatusChange = { tasca, status -> viewModel.updateTascaEstat(tasca.tasca, status) }
        )

        binding.rvTasques.apply {
            adapter = tasquAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        // Observe data
        viewModel.allTasques.observe(viewLifecycleOwner) {
            tasquAdapter.submitList(it)
        }

        // Setup add task button
        binding.btnAddTasca.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(android.R.id.content, CreateTascaFragment.newInstance(repository))
                .addToBackStack(null)
                .commit()
        }

        // Setup bottom navigation
        binding.bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                com.example.tasques.R.id.nav_tags -> {
                    parentFragmentManager.beginTransaction()
                        .replace(android.R.id.content, TagsFragment.newInstance(repository))
                        .addToBackStack(null)
                        .commit()
                    true
                }
                else -> false
            }
        }
    }
}
