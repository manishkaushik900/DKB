package com.manish.dkb.presentation.ui.compose.ui.AlbumScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.manish.dkb.data.remote.models.AlbumDtoItem
import com.manish.dkb.domain.models.AlbumItem
import com.manish.dkb.presentation.viewmodels.AlbumDetailViewModel

@Composable
fun AlbumDetailScreen(
    id: Int,
    popBackStack: () -> Unit,
    popUpToLogin: () -> Unit,
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AlbumDetailScaffoldScreen(id)
    }
}

@Composable
fun AlbumDetailScaffoldScreen(id: Int) {
    val viewModel: AlbumDetailViewModel = hiltViewModel()/*viewModel<AlbumViewModel>()*/
    viewModel.getAlbumById(id)
    val scaffoldState = rememberScaffoldState()
 /*   Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "Top App Bar") },
                elevation = 15.dp
            )
        },
        content = {
            FetchData(viewModel, modifier = Modifier.padding(it))
        }
    )*/
    ObserveDataAndPopulateView(viewModel, modifier = Modifier)
}

@Composable
fun ObserveDataAndPopulateView(viewModel: AlbumDetailViewModel, modifier: Modifier = Modifier) {

    when (val state = viewModel.uiState.collectAsState().value) {
        is AlbumDetailViewModel.AlbumDetailUiState.AlbumDetailLoaded -> {
            ShowAlbumDetail(state.album)
        }
        is AlbumDetailViewModel.AlbumDetailUiState.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(state.message)
            }
        }
        AlbumDetailViewModel.AlbumDetailUiState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun ShowAlbumDetail(album: AlbumItem) {

    Card(
        elevation = 10.dp,
        modifier = Modifier.padding(10.dp),
        content = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = album.url.toString(),
                    contentDescription = album.title.toString(),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(5.dp)
                        .clip(CircleShape)

                )
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    text = album.title.toString(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }

        },
        shape = MaterialTheme.shapes.medium
    )

}
