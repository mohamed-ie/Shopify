package com.example.shopify.feature.navigation_bar.model

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.shopify.feature.navigation_bar.model.repository.apiLayerExChange.ApiLayerExchangeRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject


@HiltWorker
class ExchangeCurrencyWorkManager @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val repository: ApiLayerExchangeRepository
): CoroutineWorker(context,params) {
    override suspend fun doWork(): Result {
        repository.updateCurrentLiveCurrencyAmount()
        return Result.success()
    }
}