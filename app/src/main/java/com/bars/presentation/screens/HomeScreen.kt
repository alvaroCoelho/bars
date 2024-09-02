package com.bars.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bars.R
import com.bars.domain.place.Place
import com.bars.presentation.screens.Routers.PLACE
import com.bars.presentation.screens.components.stateMessage
import com.bars.presentation.screens.components.toolbar
import com.bars.presentation.viewModel.ListPlacesViewModel
import com.bars.presentation.viewModel.PlacesResourceState
import com.bars.util.Constants
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: ListPlacesViewModel = hiltViewModel()
) {


    val currentState = viewModel.list.value

    val mCheckedState = remember { mutableStateOf(false) }

    var selectedPrice by remember { mutableIntStateOf(-1) }


    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        toolbar(stringResource(id = R.string.title_home))

        Row {
            PriceDropdown(selectedPrice) { price ->
                selectedPrice = price
            }
        }

        Row {
            Text(modifier = Modifier.padding(0.dp,15.dp,0.dp,3.dp),
                text = stringResource(R.string.open_now))
            Switch(
                checked = mCheckedState.value,
                onCheckedChange = { mCheckedState.value = it }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            when (currentState) {
                PlacesResourceState.Empty -> stateMessage(stringResource(id = R.string.empty))
                is PlacesResourceState.Success -> listPlaces(
                    listPlaces = currentState.places,
                    mCheckedState,
                    selectedPrice,
                    navController,
                )
                is PlacesResourceState.Loading -> stateMessage(stringResource(id = R.string.loading))
                is PlacesResourceState.Error -> stateMessage(
                    "${stringResource(id = R.string.empty)} ${currentState.message}"
                )
            }
        }
    }
}


@Composable
fun listPlaces(
    listPlaces: List<Place>,
    mCheckedState: MutableState<Boolean>,
    selectedPrice: Int,
    navController: NavController
) {

    var filteredPlaces: List<Place> = emptyList()

    if (mCheckedState.value && selectedPrice != -1) {

        filteredPlaces = listPlaces.filter { it.hour.openNow }
            .filter { it.price != null }
            .filter { it.price == selectedPrice.toString() }

    } else if (mCheckedState.value) {

        filteredPlaces = listPlaces.filter { it.hour.openNow }

    } else if (selectedPrice != -1) {

        filteredPlaces = listPlaces.filter { it.price != null }
            .filter { it.price == selectedPrice.toString() }
    } else {
        filteredPlaces = listPlaces
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(filteredPlaces) { item ->

            Card(
                modifier = Modifier
                    .padding(4.dp)
                    .clickable {
                        navController.currentBackStackEntry?.savedStateHandle?.set(PLACE, item)
                        navController.navigate("PLACE_DETAIL_SCREEN" + "/${item.id}")
                    },
                elevation = CardDefaults.cardElevation(5.dp)
            ) {
                itemPlace(place = item)
            }
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun itemPlace(place: Place) {

    Column {

        Row {

            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {

                if (place.photos.isNotEmpty()) {
                    GlideImage(
                        modifier = Modifier
                            .size(100.dp),
                        contentScale = ContentScale.Fit,
                        model = place.photos[0].prefix + Constants.PHOTO_SIZE_100 + place.photos[0].suffix,
                        contentDescription = ""
                    )
                } else {
                    Image(
                        modifier = Modifier
                            .size(100.dp),
                        painter = painterResource(id = R.drawable.baseline_no_photography_24),
                        contentDescription = ""
                    )
                }

                Row {
                    Text(color = colorResource(id = R.color.main_color),
                        text = place.name, fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        fontSize = 12.sp
                    )
                }

                Row {
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
                    Text(color = colorResource(id = R.color.main_color),
                        text = "${stringResource(id = R.string.price)} :",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )

                    Text(color = colorResource(id = R.color.main_color),
                        text = price,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }

                Row {
                    var rating = ""
                    if (place.rating != null) rating = place.rating else ""

                    Text(color = colorResource(id = R.color.main_color),
                        text = stringResource(R.string.rating),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                    Text(color = colorResource(id = R.color.main_color),
                        text = rating,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }


                Row {
                    var distance = ""
                    if (place.distance != null) distance = place.distance else ""

                    Text(color = colorResource(id = R.color.main_color),
                        text = stringResource(R.string.distance),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )

                    Text(color = colorResource(id = R.color.main_color),
                        text = distance,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}


@Composable
fun PriceDropdown(selectedPrice: Int, onPriceSelected: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val prices = listOf(-1, 1, 2, 3, 4)
    var priceText = ""

    Box {
        Button(onClick = { expanded = true }) {

            when (selectedPrice) {
                -1 -> priceText = stringResource(R.string.select_price)
                1 -> priceText = "$"
                2 -> priceText = "$$"
                3 -> priceText = "$$$"
                4 -> priceText = "$$$$"
            }

            Text(if (selectedPrice == -1) stringResource(R.string.select_price) else stringResource(
                R.string.price, priceText
            )
            )
        }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }) {
                prices.forEach { price ->
                    DropdownMenuItem({
                        when (price) {
                            -1 -> Text(stringResource(R.string.select_price))
                            1 -> Text("$")
                            2 -> Text("$$")
                            3 -> Text("$$$")
                            4 -> Text("$$$$")
                        }
                    }, onClick = {
                        onPriceSelected(
                            price
                        )
                        expanded = false
                    })
                }
            }


    }
}

