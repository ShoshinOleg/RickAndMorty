package com.shoshin.rickandmorty.fragments.characters.recycler.holders

import androidx.recyclerview.widget.RecyclerView
import com.shoshin.domain.entities.CharacterDomain
import com.shoshin.rickandmorty.databinding.CharacterHolderBinding
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation

class CharacterHolder(
    private val binding: CharacterHolderBinding,
    private val onClick: (character: CharacterDomain) -> Unit = {}
): RecyclerView.ViewHolder(binding.root) {

    private var character: CharacterDomain? = null

    init {
        binding.root.setOnClickListener { character?.let(onClick) }
    }

    fun bind(character: CharacterDomain) {
        this.character = character
        with(binding) {
            name.text = character.name
            species.text = character.species
            gender.text = character.gender

            if(character.image != null && character.image!!.isNotEmpty()) {
                val transformation = RoundedCornersTransformation(
                    8,
                    0,
                    RoundedCornersTransformation.CornerType.ALL
                )
                Picasso.get()
                    .load(character.image)
                    .centerCrop()
                    .resize(140, 140)
                    .transform(transformation)
                    .into(image)
            }
        }
    }
}