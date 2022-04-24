package com.shoshin.rickandmorty.fragments.characters.recycler.holders

import androidx.recyclerview.widget.RecyclerView
import com.shoshin.domain.entities.CharacterDomain
import com.shoshin.rickandmorty.common.images.interfaces.ImageLoader
import com.shoshin.rickandmorty.databinding.CharacterHolderBinding
import dagger.hilt.android.EntryPointAccessors

import dagger.hilt.InstallIn

import dagger.hilt.EntryPoint
import dagger.hilt.components.SingletonComponent


class CharacterHolder(
    private val binding: CharacterHolderBinding,
    private val onClick: (character: CharacterDomain) -> Unit = {}
): RecyclerView.ViewHolder(binding.root) {

    private var character: CharacterDomain? = null
    private val imageLoader: ImageLoader

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    internal interface SentenceViewHolderEntryPoint {
        fun imageLoader(): ImageLoader
    }

    init {
        binding.root.setOnClickListener { character?.let(onClick) }
        binding.image.clipToOutline = true
        val entryPoint = EntryPointAccessors.fromApplication(itemView.context.applicationContext, SentenceViewHolderEntryPoint::class.java)
        imageLoader = entryPoint.imageLoader()
    }

    fun bind(character: CharacterDomain) {
        this.character = character
        with(binding) {
            name.text = character.name
            species.text = character.species
            gender.text = character.gender

            imageLoader.load(image, character.image)
        }
    }
}