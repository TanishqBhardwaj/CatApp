package com.example.catapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.catapp.data.models.CatItem
import com.example.catapp.data.models.CatUiState
import com.example.catapp.databinding.ActivityMainBinding
import com.example.catapp.ui.adapter.CatAdapter
import com.example.catapp.viewmodels.CatViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), CatAdapter.OnItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var catAdapter: CatAdapter
    private val catViewModel by viewModels<CatViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadingState()
        getCatData()
    }

    override fun onItemClick(catItem: CatItem) {
        Intent(this, CatDetailActivity()::class.java).also {
            it.putExtra("CAT_ITEM", catItem)
            startActivity(it)
        }
    }

    private fun getCatData() {
        catViewModel.getCatData()
        lifecycleScope.launch(Dispatchers.Main) {
            catViewModel.catListState.collect { state ->
                when (state) {
                    is CatUiState.Loading -> {
                        loadingState()
                    }

                    is CatUiState.Success -> {
                        loadedState()
                        populateData(state.data)
                    }

                    is CatUiState.Error -> {
                        Snackbar.make(binding.root, state.message, Snackbar.LENGTH_LONG)
                            .setAction("Retry") {}.show()
                    }
                }
            }
        }
    }

    private fun populateData(catItemList: List<CatItem>) {
        catAdapter = CatAdapter(catItemList, this)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = catAdapter
    }

    private fun loadingState() {
        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
    }

    private fun loadedState() {
        binding.progressBar.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
    }
}