package ws.worldshine.marsroverphotos.adapters

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import ws.worldshine.marsroverphotos.viewholders.LoadStateViewHolder


class LoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<LoadStateViewHolder>() {
    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
      return LoadStateViewHolder.create(parent, retry)
    }


}

