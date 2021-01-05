package ws.worldshine.marsroverphotos.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ws.worldshine.marsroverphotos.databinding.PhotosListItemBinding

class ListViewHolder(private val binding: PhotosListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(text: String, imgSrc: String) {
        binding.tvPhotoId.text = text
        Picasso.get().load(imgSrc).into(binding.ivPhoto)
    }
}