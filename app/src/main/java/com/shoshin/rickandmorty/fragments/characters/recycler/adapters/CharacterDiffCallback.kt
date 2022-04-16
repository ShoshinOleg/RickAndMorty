package com.shoshin.rickandmorty.fragments.characters.recycler.adapters

import androidx.recyclerview.widget.DiffUtil
import com.shoshin.domain.entities.CharacterDomain

class CharacterDiffCallback: DiffUtil.ItemCallback<CharacterDomain>() {
    override fun areItemsTheSame(oldItem: CharacterDomain, newItem: CharacterDomain): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: CharacterDomain, newItem: CharacterDomain): Boolean =
        oldItem == newItem
}
