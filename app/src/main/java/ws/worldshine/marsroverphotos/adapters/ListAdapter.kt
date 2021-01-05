package ws.worldshine.marsroverphotos.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import ws.worldshine.marsroverphotos.databinding.PhotosListItemBinding
import ws.worldshine.marsroverphotos.entities.PhotosItem
import ws.worldshine.marsroverphotos.viewholders.ListViewHolder

class ListAdapter : PagingDataAdapter<PhotosItem, ListViewHolder>(COMPARATOR) {

    private var onClickListener: OnClickListener? = null
    private var onLongClickListener: OnLongClickListener? = null

    fun setOnLongClickListener(l: (item: PhotosItem, position: Int) -> Unit) {
        this.onLongClickListener = OnLongClickListener { item, position ->
            l(item, position)
        }
    }

    fun setOnClickListener(l: (PhotosItem, Int) -> Unit) {
        this.onClickListener = OnClickListener { item, position ->
            l(item, position)
        }
    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<PhotosItem>() {
            override fun areItemsTheSame(oldItem: PhotosItem, newItem: PhotosItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PhotosItem, newItem: PhotosItem): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val photosItem = getItem(position)
        photosItem?.let {
            holder.apply {
                bind(
                    text = it.id.toString(),
                    imgSrc = it.imgSrc
                )
                itemView.setOnLongClickListener {
                    onLongClickListener?.onLongLickListener(photosItem, absoluteAdapterPosition)
                    return@setOnLongClickListener true
                }
                itemView.setOnClickListener {
                    onClickListener?.onCLickListener(photosItem, layoutPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = PhotosListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ListViewHolder(binding)
    }
}

fun interface OnLongClickListener {
    fun onLongLickListener(imagesList: PhotosItem, position: Int)
}

fun interface OnClickListener {
    fun onCLickListener(imagesList: PhotosItem, position: Int)
}