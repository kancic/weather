package ancic.karim.weatherancic.data.repositories.sources.local

import ancic.karim.weatherancic.data.models.local.Forecast
import ancic.karim.weatherancic.database.WeatherDatabase
import android.location.Location
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ForecastLocalDataSource @Inject constructor(private val weatherDatabase: WeatherDatabase) {
    suspend fun save(forecast: List<Forecast>) = withContext(Dispatchers.IO) {
        weatherDatabase.forecastDao().deleteAll()
        weatherDatabase.forecastDao().insertAll(forecast)
    }

    fun load(location: Location): Flow<List<Forecast>> =
        weatherDatabase.forecastDao().load(location.latitude, location.longitude)

}
