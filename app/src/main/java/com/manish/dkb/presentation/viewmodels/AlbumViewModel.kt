package com.manish.dkb.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manish.dkb.data.remote.models.AlbumDtoItem
import com.manish.dkb.domain.usecases.GetAlbumsUseCase
import com.manish.dkb.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    private val albumListUseCase: GetAlbumsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<AlbumUiState>(AlbumUiState.Loading)
    val uiState: StateFlow<AlbumUiState> = _uiState.asStateFlow()

    init {
        getAlbumList()
    }

    /*get album list from the server*/
    fun getAlbumList() {
        viewModelScope.launch {
            _uiState.value = AlbumUiState.Loading
            when (val result = albumListUseCase.execute()) {
                is Resource.Success -> {
                    result.data.let { data ->
                        _uiState.value = AlbumUiState.AlbumListLoaded(data)
                    }
                }
                is Resource.Error -> {
                    result.message.let { error ->
                        _uiState.emit(AlbumUiState.Error(error))
                    }
                }
            }
                /*   albumListUseCase.execute().collect { results ->
                _uiState.update {
                    when (results) {
                        is Resource.Success -> {
                            results.data?.let { data ->
                                AlbumUiState.AlbumListLoaded(data)
                            } ?: AlbumUiState.Error("Unknown Error Occurred!")
                        }
                        is Resource.Error -> AlbumUiState.Error(results.message!!)

                    }

                }
            }*/

        }
    }





sealed class AlbumUiState {
    object Loading : AlbumUiState()
    data class AlbumListLoaded(val albumList: List<AlbumDtoItem>) : AlbumUiState()
    data class Error(val message: String) : AlbumUiState()
}
}
