package com.example.catapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.catapp.data.models.CatItem
import com.example.catapp.data.models.CatUiState
import com.example.catapp.databinding.ActivityMainBinding
import com.example.catapp.ui.adapter.CatAdapter
import com.example.catapp.utils.Constants
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

    private var isLoading = false
    private var isLastPage = false
    private var isScrolling = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadingState()
        setUpView()
        getCatData()
    }

    override fun onItemClick(catItem: CatItem) {
        Intent(this, CatDetailActivity()::class.java).also {
            it.putExtra(Constants.CAT_ITEM, catItem)
            startActivity(it)
        }
    }

    private fun setUpView() {
        catAdapter = CatAdapter(emptyList(), this)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = catAdapter
        binding.recyclerView.addOnScrollListener(scrollListener)
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as GridLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= Constants.PAGE_SIZE
            val shouldPaginate =
                isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                catViewModel.getCatData()
                isScrolling = false
            }
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
        catAdapter.updateList(catItemList)
        val totalPages = (catItemList.size / Constants.PAGE_SIZE) + 2
        isLastPage = catViewModel.pageNumber == totalPages
    }

    private fun loadingState() {
        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
        isLoading = true
    }

    private fun loadedState() {
        binding.progressBar.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
        isLoading = false
    }
}