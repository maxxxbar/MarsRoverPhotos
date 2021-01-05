package ws.worldshine.marsroverphotos.ui.listfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.stfalcon.imageviewer.StfalconImageViewer
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.launch
import ws.worldshine.marsroverphotos.adapters.ListAdapter
import ws.worldshine.marsroverphotos.adapters.LoadStateAdapter
import ws.worldshine.marsroverphotos.databinding.ListFragmentBinding
import javax.inject.Inject


@ExperimentalPagingApi
class ListFragment : DaggerFragment() {

    /*ViewModel*/
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<ListFragmentViewModel> { viewModelFactory }

    /*ViewBinding*/
    private var _binding: ListFragmentBinding? = null
    private val binding get() = _binding!!

    /*Views*/
    private lateinit var recyclerView: RecyclerView

    /**/
    private val adapter = ListAdapter()


    private var imageViewer: StfalconImageViewer<String>? = null
    private var imagesList = listOf<String>()

    private lateinit var layoutManager: LinearLayoutManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ListFragmentBinding.inflate(inflater, container, false)
        initialSetupAll()
        getImagesList()
        return binding.root
    }

    private fun getImagesList() {
        lifecycleScope.launch {
            viewModel.getImagesList().observe(viewLifecycleOwner) {
                imagesList = it
                imageViewer?.updateImages(imagesList)
            }
        }
    }

    private fun getPhotosFromViewModel() {
        lifecycleScope.launch {
            viewModel.getData().observe(viewLifecycleOwner) { data ->
                adapter.submitData(lifecycle, data)
            }
        }
    }

    private fun showImageViewer(position: Int) {
        val builder = StfalconImageViewer.Builder(requireContext(), imagesList) { imageView, src ->
            Picasso.get().load(src).into(imageView)
        }
        imageViewer = builder
            .withStartPosition(position)
            .withImageChangeListener { layoutManager.scrollToPosition(it) }
            .show()
    }

    private fun initialSetupAll() {
        initialSetupRecyclerView()
        initialSetupAdapter()
        initialSetupAdapterListeners()
        getPhotosFromViewModel()
    }

    private fun initialSetupAdapterListeners() {
        adapter.setOnClickListener { item, position ->
            showImageViewer(position)
            Toast.makeText(requireContext(), "Position $position", Toast.LENGTH_SHORT).show()
        }
        adapter.setOnLongClickListener { item, position ->
            viewModel.deleteItem(item)
            Toast.makeText(requireContext(), "Deleted ${item.id}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initialSetupAdapter() {
        adapter.addLoadStateListener { loadState ->
            binding.pbList.isVisible = loadState.refresh is LoadState.Loading
            binding.btnRetry.isVisible = loadState.refresh is LoadState.Error
        }
        binding.btnRetry.setOnClickListener { adapter.retry() }
    }

    private fun initialSetupRecyclerView() {
        recyclerView = binding.rvPhotosList
        layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter.withLoadStateFooter(
            footer = LoadStateAdapter { adapter.retry() }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}


