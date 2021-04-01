package com.example.kakaoapplication.ui.kakao

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.kakaoapplication.R
import com.example.kakaoapplication.data.model.Document
import com.example.kakaoapplication.databinding.ItemListBinding
import com.example.kakaoapplication.util.GlideApp
import javax.inject.Inject

class KakaoAdapter @Inject constructor(
    private var list: ArrayList<Document>,
    private val listener: OnClickListener
) : RecyclerView.Adapter<KakaoAdapter.KaKaoViewHolder>() {

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KaKaoViewHolder {
        return KaKaoViewHolder(
            ItemListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: KaKaoViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class KaKaoViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(document: Document) {
            binding.apply {
                GlideApp.with(itemView)
                    .load(document.image_url)
                    .error(R.drawable.image_not_found)
                    .into(imageQuery)

                if (document.display_sitename.isEmpty()) textSiteName.setText(R.string.empty_site_name)
                else textSiteName.text = document.display_sitename

                textDateTime.text = document.datetime.substring(0, 10)

                root.setOnClickListener {
                    listener.onClick(imageQuery, document)
                }
            }
        }
    }

    interface OnClickListener {
        fun onClick(imageView: ImageView, document: Document)
    }
}