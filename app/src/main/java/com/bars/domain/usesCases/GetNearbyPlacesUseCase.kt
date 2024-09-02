package com.bars.domain.usesCases

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import com.bars.data.model.PlacesResponse
import com.bars.di.DispatcherIO
import com.bars.repository.PlacesRepository
import com.bars.di.UseCaseDispatcher
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class GetNearbyPlacesUseCase @Inject constructor(
    private val placesRepository: PlacesRepository,
    @DispatcherIO private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(location: String): Response<PlacesResponse> =
        withContext(dispatcher) {
            return@withContext placesRepository.getNearbyPlaces(location)
        }


}