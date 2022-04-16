package com.shoshin.rickandmorty.fragments.characters.recycler.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.shoshin.rickandmorty.databinding.LoadStateHolderBinding
import com.shoshin.rickandmorty.fragments.characters.recycler.holders.LoadStateHolder
import com.shoshin.rickandmorty.fragments.characters.recycler.holders.TryAgainAction

class DefaultLoadStateAdapter (
    private val tryAgainAction: TryAgainAction
): LoadStateAdapter<LoadStateHolder>() {
    override fun onBindViewHolder(holder: LoadStateHolder, loadState: LoadState) =
        holder.bind(loadState)

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LoadStateHolderBinding.inflate(inflater, parent, false)
        return LoadStateHolder(binding, tryAgainAction)
    }
}