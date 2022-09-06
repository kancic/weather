package ancic.karim.weatherancic.models.converters

import ancic.karim.weatherancic.models.converters.base.RemoteToLocalConverter
import ancic.karim.weatherancic.models.local.Forecast
import ancic.karim.weatherancic.models.local.Weather
import ancic.karim.weatherancic.models.remote.ForecastResponse
import javax.inject.Inject

class ForecastConverter @Inject constructor() : RemoteToLocalConverter<ForecastResponse, Forecast> {
    override fun map(remote: ForecastResponse): Forecast = remote.run {
        val unit = dailyUnits.temperatureMin
        val dailyWeather = daily.time.mapIndexed { index, time ->
            val temperatureNow = if (index == 0) {
                currentWeather.temperature.toTemperature(unit)
            } else {
                null
            }
            Weather(temperatureNow, daily.temperatureMin[index].toTemperature(unit), daily.temperatureMax[index].toTemperature(unit), time)
        }
        Forecast(dailyWeather)
    }
}

private fun Double.toTemperature(unit: String): String {
    return "${this}$unit"
}
