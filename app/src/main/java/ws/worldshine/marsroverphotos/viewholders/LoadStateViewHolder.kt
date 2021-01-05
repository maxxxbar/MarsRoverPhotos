package ws.worldshine.marsroverphotos.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import ws.worldshine.marsroverphotos.R
import ws.worldshine.marsroverphotos.databinding.LoadStateFooterBinding

class LoadStateViewHolder(
    private val binding: LoadStateFooterBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): LoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.load_state_footer, parent, false)
            val binding = LoadStateFooterBinding.bind(view)
            return LoadStateViewHolder(binding, retry)
        }
    }

    init {
        binding.btnRetry.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.tvErrorMsg.text = loadState.error.localizedMessage
        }
        binding.pbErrorState.isVisible = loadState is LoadState.Loading
        binding.btnRetry.isVisible = loadState !is LoadState.Loading
        binding.tvErrorMsg.isVisible = loadState !is LoadState.Loading
    }
}