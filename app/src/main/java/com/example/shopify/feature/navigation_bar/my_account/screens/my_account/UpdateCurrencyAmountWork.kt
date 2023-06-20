package com.example.shopify.feature.navigation_bar.my_account.screens.my_account

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.shopify.feature.navigation_bar.model.repository.apiLayerExChange.ApiLayerExchangeRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class UpdateCurrencyAmountWork @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: ApiLayerExchangeRepository,
) : CoroutineWorker(appContext, workerParams) {
    companion object{
        const val NAME = "UPDATE_CURRENCY_AMOUNT_WORK"
    }

    override suspend fun doWork(): Result {
        repository.updateCurrentLiveCurrencyAmount()
        return Result.success()
    }
}