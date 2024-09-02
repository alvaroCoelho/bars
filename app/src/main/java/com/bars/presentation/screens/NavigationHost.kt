package com.bars.presentation.screens

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bars.domain.place.Place
import com.bars.presentation.screens.Routers.HOME_SCREEN
import com.bars.presentation.screens.Routers.PLACE
import com.bars.presentation.screens.Routers.PLACE_DETAIL_SCREEN
import com.bars.presentation.screens.Routers.REQUEST_PERMISSION_SCREEN
import com.bars.presentation.screens.Routers.VENUE_ID
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.maps.model.LatLng


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun NavigationHost(
    context: Context,
    locationCallback: LocationCallback,
    fusedLocationClient: FusedLocationProviderClient,
    navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = REQUEST_PERMISSION_SCREEN) {

        composable(REQUEST_PERMISSION_SCREEN) {
            requestPermissionLocation(
                context = context,
                locationCallback = locationCallback,
                fusedLocationClient = fusedLocationClient,
                navController = navController)
        }

        composable(HOME_SCREEN) {
            HomeScreen(navController = navController)
        }

        composable(
            route = PLACE_DETAIL_SCREEN,
            arguments = listOf(navArgument(VENUE_ID) { type = NavType.StringType })
        ){
            val place = navController
                .previousBackStackEntry?.savedStateHandle?.get<Place>(PLACE)
            place?.let {
                PlaceDetailsScreen(it)
            }
        }
    }



}


object Routers {
    const val REQUEST_PERMISSION_SCREEN = "REQUEST_PERMISSION_SCREEN"
    const val HOME_SCREEN = "HOME_SCREEN"
    const val PLACE_DETAIL_SCREEN = "PLACE_DETAIL_SCREEN/{venue_id}"
    const val VENUE_ID = "venue_id"
    const val PLACE = "PLACE"
}
