package com.manish.dkb.presentation.ui.compose.ui.navigation

sealed class NavRoute(val path: String) {

    object AlbumList: NavRoute("Album")

    object AlbumDetail: NavRoute("Detail"){
        val id = "id"
    }


    // build navigation path (for screen navigation)
    fun withArgs(vararg args: String): String {
        return buildString {
            append(path)
            args.forEach{ arg ->
                append("/$arg")
            }
        }
    }

    // build and setup route format (in navigation graph)
    fun withArgsFormat(vararg args: String) : String {
        return buildString {
            append(path)
            args.forEach{ arg ->
                append("/{$arg}")
            }
        }
    }
}