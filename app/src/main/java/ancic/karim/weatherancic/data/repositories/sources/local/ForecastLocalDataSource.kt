package ancic.karim.weatherancic.data.repositories.sources.local

import ancic.karim.weatherancic.data.models.local.Forecast
import ancic.karim.weatherancic.database.WeatherDatabase
import ancic.karim.weatherancic.utils.preferences.PreferencesUtil
import android.location.Location
import android.location.LocationManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ForecastLocalDataSource @Inject constructor(
    private val weatherDatabase: WeatherDatabase,
    private val preferencesUtil: PreferencesUtil
) {
    suspend fun save(forecast: List<Forecast>) = withContext(Dispatchers.IO) {
        weatherDatabase.forecastDao().deleteAll()
        weatherDatabase.forecastDao().insertAll(forecast)
    }

    fun load(location: Location): Flow<List<Forecast>> =
        weatherDatabase.forecastDao().load(location.latitude, location.longitude)

    fun loadLastLocation(): Location? = weatherDatabase.forecastDao().load()?.let { forecast ->
        Location(LocationManager.PASSIVE_PROVIDER).apply {
            longitude = forecast.longitude
            latitude = forecast.latitude
        }
    }

    fun getCheckForecastInBackground(): Boolean {
        return preferencesUtil.getCheckForecastInBackground()
    }

    fun setCheckForecastInBackground(check: Boolean) {
        preferencesUtil.setCheckForecastInBackground(check)
    }
}
