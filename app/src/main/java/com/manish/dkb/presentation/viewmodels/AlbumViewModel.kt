package com.manish.dkb.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manish.dkb.data.remote.models.AlbumDtoItem
import com.manish.dkb.domain.usecases.GetAlbumsUseCase
import com.manish.dkb.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    private val albumListUseCase: GetAlbumsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<AlbumUiState>(AlbumUiState.Loading)
    val uiState: StateFlow<AlbumUiState> = _uiState

    /*get album list from the server*/
    fun getAlbumList(){
        viewModelScope.launch {
            _uiState.value = AlbumUiState.Loading
            val result = albumListUseCase.execute()
            when(result){
                is Resource.Success -> {
                    result.data?.let {data -> _uiState.value = AlbumUiState.AlbumListLoaded(data)}
                }
                is Resource.Error-> {
                    result.message?.let { error -> _uiState.value = AlbumUiState.Error(error) }
                }
            }
        }
    }



    sealed class AlbumUiState {
        object Loading : AlbumUiState()
        class AlbumListLoaded(val albumList: List<AlbumDtoItem>) : AlbumUiState()
        class Error(val message: String) : AlbumUiState()
    }

}