package ancic.karim.weatherancic.api

import ancic.karim.weatherancic.models.remote.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDate

interface ApiService {
    @GET("forecast?current_weather=true&daily=temperature_2m_max,temperature_2m_min&timezone=auto")
    suspend fun getForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("start_date") startDate: LocalDate,
        @Query("end_date") endDate: LocalDate
    ): ForecastResponse
}
