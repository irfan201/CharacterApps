package com.example.characterapps.view.detail


import com.example.characterapps.R

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.characterapps.databinding.ActivityDetailBinding
import com.example.characterapps.model.Characters

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        val characterId = intent.getIntExtra("characterId", -1)

        viewModel.fetchCharacterDetail(characterId)

        viewModel.characterDetail.observe(this, Observer { character ->
            updateUI(character)
        })
    }

    private fun updateUI(character: Characters?) {
        // Update UI with character details
        character?.let {
            binding.tvNameDetail.text = it.name
            binding.tvStatusDetail.text = it.status
            binding.tvSpeciesDetail.text = it.species
            binding.tvGenderDetail.text = it.gender
            binding.tvOriginDetail.text = it.origin.name
            binding.tvLocationDetail.text = it.location.name
            Glide.with(this)
                .load(it.image)
                .into(binding.ivImageDetail)
        }
    }
}
