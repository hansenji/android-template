package org.jdc.template.ux.individual

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint


import kotlinx.coroutines.launch
import org.jdc.template.R
import org.jdc.template.databinding.IndividualFragmentBinding
import org.jdc.template.ui.fragment.BaseFragment

@AndroidEntryPoint
class IndividualFragment : BaseFragment() {
    private val viewModel: IndividualViewModel by viewModels()
    private lateinit var binding: IndividualFragmentBinding

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.individual_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = this@IndividualFragment
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewModelObservers()
    }

    private fun setupViewModelObservers() {
        viewModel.individual.observeKt {
            binding.individual = it
        }

        // Events
        lifecycleScope.launch {
            for (event in viewModel.eventChannel) {
                when (event) {
                    is IndividualViewModel.Event.EditIndividualEvent -> {
                        val directions = IndividualFragmentDirections.editIndividual(event.individualId)
                        findNavController().navigate(directions)
                    }
                    is IndividualViewModel.Event.IndividualDeletedEvent -> findNavController().popBackStack()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.individual_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_edit -> {
                viewModel.editIndividual()
                true
            }
            R.id.menu_item_delete -> {
                promptDeleteIndividual()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun promptDeleteIndividual() {
        MaterialAlertDialogBuilder(requireActivity())
            .setMessage(R.string.delete_individual_confirm)
            .setPositiveButton(R.string.delete) { _, _ ->
                viewModel.deleteIndividual()
            }
            .setNegativeButton(R.string.cancel) { _, _ -> }
            .show()
    }
}