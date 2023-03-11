package com.manish.dkb.presentation.ui.compose.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.manish.dkb.R
import com.manish.dkb.data.remote.models.AlbumDtoItem
import com.manish.dkb.presentation.ui.compose.AlbumListItem
import com.manish.dkb.presentation.viewmodels.AlbumViewModel
import com.manish.dkb.utils.Network
import com.manish.dkb.utils.NetworkConnectivity

@Composable
fun AlbumListScreen(
    navigateToAlbumDetail: (id: Int) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
    ) {
        AlbumScaffoldScreen(navigateToAlbumDetail)
    }
}

@Composable
fun AlbumScaffoldScreen(navigateToAlbumDetail: (id: Int) -> Unit) {

    /* val scaffoldState = rememberScaffoldState()
     Scaffold(
         scaffoldState = scaffoldState,
         topBar = {
             TopAppBar(
                 title = { Text(text = "Top App Bar") },
                 elevation = 15.dp
             )
         },
         content = {
             FetchData(navigateToAlbumDetail, modifier = Modifier.padding(it))
         }
     )*/

    FetchData(navigateToAlbumDetail, modifier = Modifier)
}


@Composable
fun FetchData(navigateToAlbumDetail: (id: Int) -> Unit, modifier: Modifier = Modifier) {
    val viewModel: AlbumViewModel = hiltViewModel()/*viewModel<AlbumViewModel>()*/

    when (val state = viewModel.uiState.collectAsState().value) {
        is AlbumViewModel.AlbumUiState.AlbumListLoaded -> {
            AlbumList(listState = state.albumList, navigateToAlbumDetail)
        }
        is AlbumViewModel.AlbumUiState.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(state.message)
            }
        }
        AlbumViewModel.AlbumUiState.Loading -> {
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
fun AlbumList(listState: List<AlbumDtoItem>, navigateToAlbumDetail: (id: Int) -> Unit) {
    val lazyListState = rememberLazyListState()
    val context = LocalContext.current
    val networkConnectivity: NetworkConnectivity = Network(context)
    var isInternet by remember { mutableStateOf(networkConnectivity.isNetworkAvailable()) }
    LazyColumn(
        state = lazyListState,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)

    ) {
        item {
            Spacer(modifier = Modifier.height(12.dp))
        }

        items(listState.size) {

            AlbumListItem(item = listState[it]) { item ->
                if (networkConnectivity.isNetworkAvailable()) {
                    navigateToAlbumDetail(item.id!!)
                }else{
                    Toast.makeText(context, context.getText(R.string.msg_no_internet), Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}

/*@Composable
fun ErrorDialog(message: String) {
    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = stringResource(R.string.problem_occurred))
            },
            text = {
                Text(message)
            },
            confirmButton = {
                openDialog.value = false
            }
        )
    }
}*/
