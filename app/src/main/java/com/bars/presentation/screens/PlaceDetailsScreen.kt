package com.bars.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import com.bars.R
import com.bars.domain.place.Photo
import com.bars.domain.place.Place
import com.bars.domain.tips.TipsModel
import com.bars.presentation.screens.components.stateMessage
import com.bars.presentation.screens.components.toolbar
import com.bars.presentation.viewModel.PlaceDetailsViewModel
import com.bars.presentation.viewModel.TipsResourceState
import com.bars.util.Constants
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun PlaceDetailsScreen(
    place: Place,
    viewModel: PlaceDetailsViewModel = hiltViewModel()
) {

    val currentState = viewModel.list.value

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

            toolbar(place.name)

            photoPlace(place)

            infosPlace(place)

            placeListPhotos(place.photos)

        tipsList(currentState)

        }

}

@Composable
private fun tipsList(currentState: TipsResourceState) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        when (currentState) {
            TipsResourceState.Empty -> stateMessage(stringResource(id = R.string.empty))
            is TipsResourceState.Success -> listTips(listTips = currentState.tips)
            is TipsResourceState.Loading -> stateMessage(stringResource(id = R.string.loading))
            is TipsResourceState.Error -> stateMessage("${stringResource(id = R.string.empty)} ${currentState.message}")
            else -> {}
        }
    }
}

@Composable
private fun infosPlace(place: Place) {
    Row(modifier = Modifier.padding(5.dp, 5.dp, 5.dp, 5.dp)) {
        var price = ""

        if (place.price != null) {
            when (place.price) {
                (-1).toString() -> price = stringResource(R.string.select_price)
                1.toString() -> price = "$"
                2.toString() -> price = "$$"
                3.toString() -> price = "$$$"
                4.toString() -> price = "$$$$"
            }
        }

        Text(
            text = price, fontWeight = FontWeight.Bold
        )
    }

    Row(modifier = Modifier.padding(5.dp, 5.dp, 5.dp, 5.dp)) {
        var phone = ""
        if (place.phone != null) phone = place.phone else ""

        Text(
            text = phone, fontWeight = FontWeight.Bold
        )
    }

    Row(modifier = Modifier.padding(5.dp, 5.dp, 5.dp, 5.dp)) {
        var address = ""
        if (place.location.address != null) address = place.location.address else ""

        Text(
            text = address, fontWeight = FontWeight.Bold
        )
    }


    Row(modifier = Modifier.padding(5.dp, 5.dp, 5.dp, 5.dp)) {
        var rating = ""
        if (place.rating != null) rating = place.rating else ""

        Text(
            text = rating, fontWeight = FontWeight.Bold
        )
    }


    Row(modifier = Modifier.padding(5.dp, 5.dp, 5.dp, 5.dp)) {
        var availability = ""
        if (place.hour.display != null) availability = place.hour.display else ""

        Text(
            text = availability, fontWeight = FontWeight.Bold
        )
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun photoPlace(place: Place) {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        if (place.photos.isNotEmpty()) {
            GlideImage(
                modifier = Modifier
                    .size(220.dp),
                contentScale = ContentScale.Fit,
                model = place.photos[0].prefix + Constants.PHOTO_SIZE_200 + place.photos[0].suffix,
                contentDescription = ""
            )
        } else {
            Image(
                modifier = Modifier
                    .size(200.dp),
                painter = painterResource(id = R.drawable.baseline_no_photography_24),
                contentDescription = ""
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun placeItem(photo: Photo) {
    GlideImage(
        modifier = Modifier
            .size(100.dp),
        contentScale = ContentScale.Fit,
        model = photo.prefix + Constants.PHOTO_SIZE_100 + photo.suffix,
        contentDescription = ""
    )
}

@Composable
fun placeListPhotos(photos: List<Photo>) {

    Row {
        LazyRow {
            items(photos) { photo ->
                placeItem(photo = photo)
            }
        }
    }

}

@Composable
fun listTips(
    listTips: List<TipsModel>
) {

    var list = listTips.filter{ it.tipPhoto != null }
    LazyColumn{

        items(list) { tip ->
                tipsItem(tipPhoto = tip.tipPhoto)
        }
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun tipsItem(tipPhoto: com.bars.domain.tips.TipPhoto) {
    GlideImage(
        modifier = Modifier
            .size(100.dp),
        contentScale = ContentScale.Fit,
        model = tipPhoto.prefix + Constants.PHOTO_SIZE_100 + tipPhoto.suffix,
        contentDescription = ""
    )
}