package com.manish.dkb.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.google.android.material.snackbar.Snackbar
import com.manish.dkb.R
import com.manish.dkb.data.remote.models.AlbumDtoItem
import com.manish.dkb.databinding.FragmentAlbumBinding
import com.manish.dkb.presentation.adapter.AlbumAdapter
import com.manish.dkb.presentation.viewmodels.AlbumViewModel
import com.manish.dkb.utils.NetworkConnectivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class AlbumFragment: Fragment(), AlbumAdapter.AlbumItemListener {

    private lateinit var binding: FragmentAlbumBinding
    private val viewModel: AlbumViewModel by viewModels()
    private lateinit var adapter: AlbumAdapter
    @Inject
    lateinit var networkConnectivity: NetworkConnectivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAlbumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        initData()

        swipeToRefresh()
    }

    /*Call the album list use case and fetch the list of album photos*/
    private fun initData() {
        viewModel.getAlbumList()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is AlbumViewModel.AlbumUiState.AlbumListLoaded -> onAlbumListLoaded(state.albumList)
                        is AlbumViewModel.AlbumUiState.Error -> showError(state.message)
                        is AlbumViewModel.AlbumUiState.Loading -> showLoading()
                    }
                }
            }
        }
    }

    /*Set up the recyclerview and adapter and attach adapter to recyclerview*/
    private fun setupRecyclerView() {
        adapter = AlbumAdapter(this)
        binding.apply {
            albumRecyclerview.layoutManager = LinearLayoutManager(requireContext())
            albumRecyclerview.adapter = adapter
        }
    }

    private fun swipeToRefresh() {
        binding.swipeContainer.setOnRefreshListener {
            viewModel.getAlbumList()
            binding.swipeContainer.isRefreshing = false
        }
    }

    /*populate the album list data to adapter list*/
    private fun onAlbumListLoaded(albumList: List<AlbumDtoItem>) {
        hideLoading()
        adapter.setItems(albumList)
    }

    /*Show error message to user*/
    private fun showError(message: String) {
        hideLoading()
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    /*Show progressbar*/
    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    /*hide progress bar*/
    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    /*On album list item click open the album details*/
    override fun onClickedAlbum(photoId: Int) {
        if (!networkConnectivity.isNetworkAvailable()) {
            showError("No Internet Connection")
            return
        }
        val bundle = bundleOf("photoId" to photoId)
        view?.findNavController()?.navigate(R.id.albumDetailFragment, bundle)

    }

    /*End*/
}