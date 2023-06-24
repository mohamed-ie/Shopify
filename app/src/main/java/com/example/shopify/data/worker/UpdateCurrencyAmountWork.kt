package com.example.shopify.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.shopify.data.api_layer_exchange.repository.ApiLayerExchangeRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class UpdateCurrencyAmountWork @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: ApiLayerExchangeRepository,
) : CoroutineWorker(appContext, workerParams) {
    companion object {
        const val NAME = "UPDATE_CURRENCY_AMOUNT_WORK"
    }

    override suspend fun doWork(): Result {
        repository.updateCurrentLiveCurrencyAmount()
        return Result.success()
    }
}