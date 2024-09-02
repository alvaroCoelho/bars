package com.bars.domain.usesCases

import android.location.Location
import com.bars.di.DispatcherIO
import com.bars.domain.location.LocationTracker
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetUserLocationUseCase  @Inject constructor(
    @DispatcherIO private val dispatcher: CoroutineDispatcher,
    private val locationTracker: LocationTracker
) {

    suspend operator fun invoke(): Location? =
        withContext(dispatcher) {
            return@withContext locationTracker.getCurrentLocation()
        }

}