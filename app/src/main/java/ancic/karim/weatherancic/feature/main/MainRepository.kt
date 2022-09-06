package ancic.karim.weatherancic.feature.main

import ancic.karim.weatherancic.api.ApiService
import ancic.karim.weatherancic.models.converters.ForecastConverter
import ancic.karim.weatherancic.models.local.Forecast
import android.location.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import javax.inject.Inject


class MainRepository @Inject constructor(private val apiService: ApiService, private val forecastConverter: ForecastConverter) {
    fun getForecast(location: Location): Flow<Result<Forecast>> = flow {
        val startDate = LocalDate.now()
        val endDate = startDate.plusDays(3)
        emit(Result.success(apiService.getForecast(location.latitude, location.longitude, startDate, endDate).let {
            forecastConverter.map(it)
        }))
    }.catch {
        emit(Result.failure(it))
    }
}