package com.korett.funnycat.presentation.gallery

import android.content.res.TypedArray
import android.graphics.drawable.ColorDrawable
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.korett.funnycat.databinding.ItemCatBinding
import com.korett.funnycat.domain.model.Cat


class GalleryAdapter(private val dataSet: List<Cat>) :
    RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    private var listener: OnItemClickListener? = null
    private var placeholder: ColorDrawable? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)

    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(val binding: ItemCatBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val a: TypedArray = parent.context.obtainStyledAttributes(
            TypedValue().data,
            intArrayOf(android.R.attr.colorAccent)
        )
        placeholder = ColorDrawable(a.getColor(0, 0))
        a.recycle()
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(dataSet[position]) {
                Glide.with(holder.itemView.context)
                    .load(imagePath)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .placeholder(placeholder)
                    .into(binding.imCat)
                binding.cardCat.setOnClickListener {
                    listener?.onItemClick(position)
                }
            }
        }
    }

    override fun getItemCount(): Int = dataSet.size

}