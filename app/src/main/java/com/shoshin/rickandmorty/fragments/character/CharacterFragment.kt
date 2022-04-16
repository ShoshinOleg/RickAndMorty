package com.shoshin.rickandmorty.fragments.character

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.shoshin.domain.common.Reaction
import com.shoshin.domain.entities.CharacterDomain
import com.shoshin.rickandmorty.R
import com.shoshin.rickandmorty.common.argument
import com.shoshin.rickandmorty.databinding.CharacterFragmentBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterFragment: Fragment(R.layout.character_fragment) {
    private val binding by viewBinding(CharacterFragmentBinding::bind)
    private val viewModel: CharacterViewModel by viewModels()
    private val characterId: Int by argument()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.image.minimumHeight = requireActivity().window.decorView.width

        subscribeCharacter()
        binding.loadStateHolder.tryAgainButton.setOnClickListener {
            viewModel.getCharacter(characterId)
        }
        viewModel.getCharacter(characterId)
    }

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

        if(character.image != null && character.image!!.isNotEmpty()) {
            Picasso.get()
                .load(character.image)
                .into(binding.image)
        }

        binding.loadStateHolder.errorLayout.isVisible = false
        binding.loadStateHolder.progressBar.isVisible = false
        binding.mainLayout.isVisible = true
    }

    private fun toProgressMode() {
        binding.mainLayout.isVisible = false
        binding.loadStateHolder.errorLayout.isVisible = false
        binding.loadStateHolder.progressBar.isVisible = true
    }

    private fun toErrorMode() {
        binding.mainLayout.isVisible = false
        binding.loadStateHolder.progressBar.isVisible = false
        binding.loadStateHolder.errorLayout.isVisible = true
    }
}