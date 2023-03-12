package com.manish.dkb.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manish.dkb.domain.models.AlbumItem
import com.manish.dkb.domain.usecases.GetAlbumByIdUseCase
import com.manish.dkb.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumDetailViewModel @Inject constructor(
    private val albumByIdUseCase: GetAlbumByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<AlbumDetailUiState>(AlbumDetailUiState.Loading)
    val uiState: StateFlow<AlbumDetailUiState> = _uiState

    /*get album based on photo id from the server*/
    fun getAlbumById(id:Int){
        viewModelScope.launch {
            _uiState.value = AlbumDetailUiState.Loading
            when(val result = albumByIdUseCase.execute(id)){
                is Resource.Success -> {
                    result.data.let { data -> _uiState.value = AlbumDetailUiState.AlbumDetailLoaded(data)}
                }
                is Resource.Error-> {
                    result.message.let { error -> _uiState.value = AlbumDetailUiState.Error(error) }
                }
            }
        }
    }

    sealed class AlbumDetailUiState {
        object Loading : AlbumDetailUiState()
        class AlbumDetailLoaded(val album: AlbumItem) : AlbumDetailUiState()
        class Error(val message: String) : AlbumDetailUiState()
    }

}