package com.manish.dkb.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.manish.dkb.data.remote.models.AlbumDtoItem
import com.manish.dkb.databinding.AlbumItemRowBinding
import com.manish.dkb.utils.loadProfilePhoto

/*Adapter to populate the list to album fragment*/
class AlbumAdapter(private val listener: AlbumItemListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface AlbumItemListener {
        fun onClickedAlbum(photoId: Int)
    }

    private val items = ArrayList<AlbumDtoItem>()

    fun setItems(items: List<AlbumDtoItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val binding: AlbumItemRowBinding =
            AlbumItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AlbumViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

}

class AlbumViewHolder(
    private val itemBinding: AlbumItemRowBinding,
    private val listener: AlbumAdapter.AlbumItemListener
) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var albumItem: AlbumDtoItem

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: AlbumDtoItem) {
        this.albumItem = item
        itemBinding.title.text = item.title
        itemBinding.photoId.text = "${item.id}"
        itemBinding.image.loadProfilePhoto(item.thumbnailUrl, itemBinding.image.context)
    }

    override fun onClick(v: View?) {
        listener.onClickedAlbum(albumItem.id!!)
    }
}

