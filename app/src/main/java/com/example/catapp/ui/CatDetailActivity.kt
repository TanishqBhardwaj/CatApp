package com.example.catapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import coil.load
import coil.size.Scale
import com.example.catapp.data.models.CatItem
import com.example.catapp.databinding.ActivityCatDetailBinding

class CatDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCatDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()  // to handle back navigation
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val catItem = intent.getSerializableExtra("CAT_ITEM") as CatItem
        supportActionBar?.title = catItem.name
        binding.catDetailImageView.load(catItem.imageUrl) {
            scale(Scale.FIT)
        }
        binding.textViewOriginVal.text = catItem.origin
        binding.textViewTemperamentVal.text = catItem.temperament
        binding.textViewLifeSpanVal.text = catItem.lifeSpan
        binding.textViewDescriptionVal.text = catItem.description
    }
}