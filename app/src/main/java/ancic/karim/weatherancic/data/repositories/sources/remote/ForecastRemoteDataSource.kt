package ancic.karim.weatherancic.data.repositories.sources.remote

import ancic.karim.weatherancic.api.ApiService
import ancic.karim.weatherancic.data.models.remote.ForecastResponse
import android.location.Location
import java.time.LocalDate
import javax.inject.Inject

class ForecastRemoteDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun getForecast(location: Location): ForecastResponse {
        val startDate = LocalDate.now()
        val endDate = startDate.plusDays(3)
        return apiService.getForecast(location.latitude, location.longitude, startDate, endDate)
    }

}
