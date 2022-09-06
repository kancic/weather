package ancic.karim.weatherancic.models.remote

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("current_weather") val currentWeather: CurrentWeatherResponse,
    @SerializedName("daily") val daily: DailyResponse,
    @SerializedName("daily_units") val dailyUnits: DailyUnitsResponse,
    @SerializedName("elevation") val elevation: Double,
    @SerializedName("generationtime_ms") val generationTimeMs: Double,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("timezone") val timezone: String,
    @SerializedName("timezone_abbreviation") val timezoneAbbreviation: String,
    @SerializedName("utc_offset_seconds") val utcOffsetSeconds: Int
)

data class CurrentWeatherResponse(
    @SerializedName("temperature") val temperature: Double,
    @SerializedName("time") val time: String,
    @SerializedName("weathercode") val weatherCode: Double,
    @SerializedName("winddirection") val windDirection: Double,
    @SerializedName("windspeed") val windSpeed: Double
)

data class DailyResponse(
    @SerializedName("temperature_2m_max") val temperatureMax: List<Double>,
    @SerializedName("temperature_2m_min") val temperatureMin: List<Double>,
    @SerializedName("time") val time: List<String>
)

data class DailyUnitsResponse(
    @SerializedName("temperature_2m_max") val temperatureMax: String,
    @SerializedName("temperature_2m_min") val temperatureMin: String,
    @SerializedName("time") val time: String
)