package com.example.catapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.catapp.R
import com.example.catapp.data.models.CatItem

class CatAdapter(
    private var catList: List<CatItem>,
    private val listener: OnItemClickListener
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

    fun updateList(newList: List<CatItem>) {
        catList = newList
        notifyDataSetChanged() // Notify adapter about the change
    }

    interface OnItemClickListener {
        fun onItemClick(catItem: CatItem)
    }

    inner class CatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val catImageView: ImageView = itemView.findViewById(R.id.catImageView)
        private val catTextView: TextView = itemView.findViewById(R.id.catTextView)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(catItem: CatItem) {
            catImageView.load(catItem.imageUrl) {
                scale(Scale.FIT)
            }
            catTextView.text = catItem.name
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(catList[position])
            }
        }
    }
}