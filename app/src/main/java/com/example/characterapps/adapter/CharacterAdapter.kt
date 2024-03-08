package com.example.characterapps.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.characterapps.databinding.ItemCharacterBinding
import com.example.characterapps.model.Characters

class CharacterAdapter : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    private val characterList = mutableListOf<Characters>()
    private var onItemClick: ((Characters) -> Unit)? = null

    fun setOnItemClickListener(listener: (Characters) -> Unit) {
        onItemClick = listener
    }

    fun setCharacterList(characters: List<Characters>) {
        characterList.clear()
        characterList.addAll(characters)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharacterViewHolder {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    inner class CharacterViewHolder(private val binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Characters) {
            binding.characterName.text = character.name
            binding.characterStatus.text = character.status
            binding.characterSpecies.text = character.species
            Glide.with(binding.root.context)
                .load(character.image)
                .into(binding.characterImage)

            itemView.setOnClickListener {
                onItemClick?.invoke(character)
            }
        }
    }

    override fun onBindViewHolder(holder: CharacterAdapter.CharacterViewHolder, position: Int) {
        val character = characterList[position]
        holder.bind(character)
    }

    override fun getItemCount(): Int {
        return characterList.size
    }
}
