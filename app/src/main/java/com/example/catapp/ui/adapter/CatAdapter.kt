package com.example.catapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.catapp.R
import com.example.catapp.data.models.CatItem

class CatAdapter(
    private val catList: List<CatItem>
): RecyclerView.Adapter<CatAdapter.CatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.cat_item, parent, false)
        return CatViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val currentItem = catList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return catList.size
    }

    inner class CatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewDay: TextView = itemView.findViewById(R.id.textView)

        fun bind(catItem: CatItem) {
            textViewDay.text = catItem.name
        }
    }
}