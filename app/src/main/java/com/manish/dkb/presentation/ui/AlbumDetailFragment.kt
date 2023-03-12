package com.manish.dkb.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.manish.dkb.R
import com.manish.dkb.databinding.FragmentAlbumDetailBinding
import com.manish.dkb.domain.models.AlbumItem
import com.manish.dkb.presentation.viewmodels.AlbumDetailViewModel
import com.manish.dkb.utils.loadProfilePhoto
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AlbumDetailFragment : Fragment() {

    private lateinit var binding: FragmentAlbumDetailBinding
    private val viewModel: AlbumDetailViewModel by viewModels()
    private val args: AlbumDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAlbumDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
//            arguments?.getInt("photoId")?.let { initData(it) }
            initData(args.photoId)
        } catch (e: java.lang.Exception) {
            error(e.message.toString())
        }

    }

    /*fetch the album detail data*/
    private fun initData(photoId: Int) {

        viewModel.getAlbumById(photoId)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is AlbumDetailViewModel.AlbumDetailUiState.AlbumDetailLoaded -> onAlbumLoaded(state.album)
                        is AlbumDetailViewModel.AlbumDetailUiState.Error -> showError(state.message)
                        is AlbumDetailViewModel.AlbumDetailUiState.Loading -> showLoading()
                    }
                }
            }
        }
    }

    private fun onAlbumLoaded(album: AlbumItem) {
        hideLoading()
        setView(album)
    }

    private fun showError(message: String) {
        hideLoading()
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    /* Show progressbar*/
    private fun showLoading() {
        (activity as AlbumActivity).showLoading()
    }

    /*hide progress bar*/
    private fun hideLoading() {
        (activity as AlbumActivity).hideLoading()
    }

    /*set the data to view*/
    private fun setView(item: AlbumItem) {
        try {
            binding.image.loadProfilePhoto(item.url,  binding.image.context)
            binding.title.text = item.title
        } catch (e: java.lang.Exception) {
            Toast.makeText(requireActivity(), getString(R.string.msg_image_not_loaded), Toast.LENGTH_SHORT).show()
        }

    }

    /*End*/
}