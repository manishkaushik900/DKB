package com.manish.dkb.presentation.ui.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.manish.dkb.presentation.ui.compose.ui.navigation.NavGraph
import com.manish.dkb.presentation.ui.compose.ui.theme.DKBTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DKBTheme {
                MainScreen()
            }
        }
    }
}

@Composable
private fun MainScreen() {
    DKBTheme {
        val navController = rememberNavController()
        val scaffoldState = rememberScaffoldState()
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(
                    title = { Text(text = "Top App Bar") },
                    elevation = 15.dp
                )
            },
            content = {
//                FetchData(viewModel, modifier = Modifier.padding(it))
                NavGraph(navController,modifier = Modifier.padding(it))
            }
        )
//        NavGraph(navController)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DKBTheme {

    }
}