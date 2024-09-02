package com.bars.domain.usesCases

import com.bars.di.DispatcherIO
import com.bars.domain.tips.TipsModel
import com.bars.repository.VenueRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class GetTipsUseCase @Inject constructor(
    private val venueRepository: VenueRepository,
    @DispatcherIO private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(idVenue: String): Response<List<TipsModel>> =
        withContext(dispatcher) {
            return@withContext venueRepository.getTips(idVenue)
        }
}