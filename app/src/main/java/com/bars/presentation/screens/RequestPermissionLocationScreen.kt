package com.bars.presentation.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Looper
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.bars.R
import com.bars.presentation.screens.Routers.HOME_SCREEN
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun requestPermissionLocation(
    context: Context,
    locationCallback: LocationCallback,
    fusedLocationClient: FusedLocationProviderClient,
    navController: NavController
) {

     val  permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    val messagePermission = stringResource(id = R.string.you_need_permission_location_to_continue)

    val launcherMultiplePermissions = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()) {
            permissionsMap ->
        val areGranted = permissionsMap.values.reduce{ acc, next -> acc && next }
        if (areGranted) {
            startLocationUpdates(locationCallback, fusedLocationClient)
        } else {
            Toast.makeText(context, messagePermission, Toast.LENGTH_SHORT).show()
        }
    }

    Box (modifier = Modifier.fillMaxSize()){
        Column (modifier = Modifier
            .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Button(modifier = Modifier.size(300.dp,50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.main_color) ),
                onClick = {

                if (permissions.all {
                        ContextCompat.checkSelfPermission(context,it) == PackageManager.PERMISSION_GRANTED
                    })
                {
                    startLocationUpdates(locationCallback, fusedLocationClient)
                    navController.navigate(HOME_SCREEN)
                } else {
                    launcherMultiplePermissions.launch(permissions)
                }


            }) {
                Text(text =
                stringResource(R.string.find) +" " +
                        stringResource(R.string.shop) + " " +
                        stringResource(R.string.of) + " " +
                        stringResource(R.string.venue) + " " +
                        stringResource(R.string.near_you)
                )
            }
        }
    }
}

@SuppressLint("MissingPermission")
private fun startLocationUpdates(
    locationCallback: LocationCallback,
    fusedLocationClient: FusedLocationProviderClient
) {
    locationCallback?.let {
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 100
        )
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(3000)
            .setMaxUpdateDelayMillis(100)
            .build()


        fusedLocationClient?.requestLocationUpdates(
            locationRequest,
            it,
            Looper.getMainLooper()
        )
    }
}