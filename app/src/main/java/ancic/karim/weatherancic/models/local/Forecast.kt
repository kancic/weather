package ancic.karim.weatherancic.models.local

data class Forecast(
    val dailyWeather: List<Weather>
)

data class Weather(
    val temperatureNow: String?,
    val temperatureMin: String,
    val temperatureMax: String,
    val date: String,
)