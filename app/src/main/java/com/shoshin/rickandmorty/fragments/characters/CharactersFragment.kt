package com.shoshin.rickandmorty.fragments.characters

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.shoshin.domain.entities.CharacterDomain
import com.shoshin.rickandmorty.R
import com.shoshin.rickandmorty.common.simpleScan
import com.shoshin.rickandmorty.databinding.CharactersFragmentBinding
import com.shoshin.rickandmorty.fragments.characters.recycler.adapters.CharacterAdapter
import com.shoshin.rickandmorty.fragments.characters.recycler.adapters.DefaultLoadStateAdapter
import com.shoshin.rickandmorty.fragments.characters.recycler.holders.LoadStateHolder
import com.shoshin.rickandmorty.fragments.characters.recycler.holders.TryAgainAction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharactersFragment: Fragment(R.layout.characters_fragment) {
    private val binding by viewBinding(CharactersFragmentBinding::bind)
    private val viewModel: CharactersViewModel by viewModels()
    private var adapter = CharacterAdapter(::onCharacterClick)
    private var mainLoadStateHolder: LoadStateHolder? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainLoadStateHolder = LoadStateHolder(
            binding.loadStateView,
            adapter::retry,
            binding.swipeRefreshLayout
        )
        setupCharacters()
        setupSwipeToRefresh()
    }

    private fun setupSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            adapter.refresh()
        }
    }

    private fun setupCharacters() {
        val tryAgainAction: TryAgainAction = adapter::retry
        val footerAdapter = DefaultLoadStateAdapter(tryAgainAction)
        val adapterWithLoadState = adapter.withLoadStateFooter(footerAdapter)

        binding.reviewsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.reviewsRecyclerView.adapter = adapterWithLoadState

        observeLoadState()
        handleListVisibility()
        observeCharacters()
    }

    private fun observeCharacters() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.charactersFlow.collect { pagingData ->
                adapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
            }
        }
    }

    private fun observeLoadState() {
        viewLifecycleOwner.lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { state ->
                mainLoadStateHolder?.bind(state.refresh)
            }
        }
    }

    private fun handleListVisibility() = lifecycleScope.launch {
        adapter.loadStateFlow.map { it.refresh }
            .simpleScan(count = 3)
            .collectLatest { (beforePrevious, previous, current) ->
                binding.reviewsRecyclerView.isInvisible = current is LoadState.Error
                        || previous is LoadState.Error
                        || (beforePrevious is LoadState.Error && previous is LoadState.NotLoading
                        && current is LoadState.Loading)
            }
    }

    private fun onCharacterClick(character: CharacterDomain) {
        if(character.id != null) {
            val directions = CharactersFragmentDirections.toCharacter(character.id!!)
            findNavController().navigate(directions)
        }
    }
}