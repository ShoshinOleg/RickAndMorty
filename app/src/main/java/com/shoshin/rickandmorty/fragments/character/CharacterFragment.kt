package com.shoshin.rickandmorty.fragments.character

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.shoshin.domain.common.Reaction
import com.shoshin.domain.entities.CharacterDomain
import com.shoshin.rickandmorty.R
import com.shoshin.rickandmorty.common.images.interfaces.ImageLoader
import com.shoshin.rickandmorty.databinding.CharacterFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CharacterFragment: Fragment(R.layout.character_fragment) {
    private val binding by viewBinding(CharacterFragmentBinding::bind)
    private val viewModel: CharacterViewModel by viewModels()
    private val args: CharacterFragmentArgs by navArgs()
    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.image.minimumHeight = requireActivity().window.decorView.width
        binding.swipeRefreshLayout.setOnRefreshListener { updateCharacter() }
        subscribeCharacter()
        binding.loadStateHolder.tryAgainButton.setOnClickListener { updateCharacter() }
        toDataMode(args.character)
    }

    private fun updateCharacter() = viewModel.getCharacter(args.character.id)

    private fun subscribeCharacter() {
        viewModel.character.observe(viewLifecycleOwner) { reaction ->
            when(reaction) {
                is Reaction.Success -> toDataMode(reaction.data)
                is Reaction.Progress -> toProgressMode()
                is Reaction.Error -> toErrorMode()
            }
        }
    }

    private fun toDataMode(character: CharacterDomain){
        binding.name.text = character.name
        binding.episodes.text = context?.getString(
            R.string.episodes_count,
            character.episode?.size ?: 0
        )
        binding.species.text = context?.getString(R.string.species, character.species ?: "")
        binding.gender.text = context?.getString(R.string.gender, character.gender ?: "")
        binding.status.text = context?.getString(R.string.status, character.status ?: "")
        binding.location.text = context?.getString(R.string.status, character.location?.name ?: "")

        imageLoader.load(binding.image, character.image)

        binding.loadStateHolder.errorLayout.isVisible = false
        binding.mainLayout.isVisible = true
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun toProgressMode() {
        binding.swipeRefreshLayout.isRefreshing = true
        binding.loadStateHolder.errorLayout.isVisible = false
    }

    private fun toErrorMode() {
        binding.mainLayout.isVisible = false
        binding.loadStateHolder.progressBar.isVisible = false
        binding.loadStateHolder.errorLayout.isVisible = true
        binding.swipeRefreshLayout.isRefreshing = false
    }
}