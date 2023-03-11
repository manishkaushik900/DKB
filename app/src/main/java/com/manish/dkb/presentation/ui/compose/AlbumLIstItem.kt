package com.manish.dkb.presentation.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.manish.dkb.data.remote.models.AlbumDtoItem


@Composable
fun AlbumListItem(item: AlbumDtoItem, clickListener: (AlbumDtoItem) -> Unit) {

    ConstraintLayout(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxWidth()
            .clickable { clickListener(item) }
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {

//        var stared by remember(item.id) { mutableStateOf(false) }

        val (image, title, subtitle) = createRefs()

        createVerticalChain(title, subtitle, chainStyle = ChainStyle.Packed)
        AsyncImage(
            model = item.thumbnailUrl.toString(),
            contentDescription = item.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .constrainAs(image) {
                    linkTo(start = parent.start, end = title.start)
                    top.linkTo(title.top)
                }

        )
        Text(
            text = item.id.toString(),
            style = MaterialTheme.typography.caption,
            modifier = Modifier.constrainAs(title) {
                start.linkTo(image.end, 16.dp)
                width = Dimension.fillToConstraints
            }
        )

        Text(
            text = item.title.toString(),
            style = MaterialTheme.typography.subtitle1.copy(fontSize = 14.sp),
            modifier = Modifier.constrainAs(subtitle) {
                start.linkTo(image.end, 16.dp)
                width = Dimension.fillToConstraints
            }.padding(end= 20.dp)
        )
    }
}

@Composable
@Preview
fun PreviewGmailItem() {
    val item = albumDtoSample
    AlbumListItem(item = item){}
}