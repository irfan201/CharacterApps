package com.example.characterapps.view.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.characterapps.adapter.CharacterAdapter
import com.example.characterapps.databinding.ActivityMainBinding
import com.example.characterapps.view.detail.DetailActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var characterAdapter: CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        characterAdapter = CharacterAdapter()

        binding.rvCharacter.layoutManager = LinearLayoutManager(this)

        binding.rvCharacter.adapter = characterAdapter

        viewModel.characterList.observe(this, { characters ->
            characterAdapter.setCharacterList(characters)
        })

        characterAdapter.setOnItemClickListener { character ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("characterId", character.id)
            startActivity(intent)
        }

        binding.rvCharacter.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!viewModel.isLoading && !viewModel.isLastPage) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                        viewModel.fetchData()
                    }
                }
            }
        })
    }
}
