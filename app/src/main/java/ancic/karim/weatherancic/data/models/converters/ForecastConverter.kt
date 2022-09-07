package ancic.karim.weatherancic.data.models.converters

import ancic.karim.weatherancic.data.models.converters.base.RemoteToLocalConverter
import ancic.karim.weatherancic.data.models.local.Forecast
import ancic.karim.weatherancic.data.models.local.Precipitation
import ancic.karim.weatherancic.data.models.remote.ForecastResponse
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class ForecastConverter @Inject constructor() : RemoteToLocalConverter<ForecastResponse, List<Forecast>> {
    override fun map(remote: ForecastResponse): List<Forecast> = remote.run {
        val unit = dailyUnits.temperatureMin
        val dailyForecast = daily.time.mapIndexed { index, time ->
            val date = LocalDate.parse(time)
            val temperatureNow = if (index == 0) {
                currentWeather.temperature.toTemperature(unit)
            } else {
                null
            }
            val precipitation = if (index == 0) {
                hourly.precipitation.withIndex().find { item -> item.value > 0.0 }?.let { item ->
                    Precipitation(item.value, LocalDateTime.parse(hourly.time[item.index]), hourlyUnits.precipitation)
                }
            } else {
                null
            }
            Forecast(
                date,
                latitude,
                longitude,
                temperatureNow,
                daily.temperatureMin[index].toTemperature(unit),
                daily.temperatureMax[index].toTemperature(unit),
                precipitation
            )
        }
        dailyForecast
    }
}

private fun Double.toTemperature(unit: String): String {
    return "${this}$unit"
}
