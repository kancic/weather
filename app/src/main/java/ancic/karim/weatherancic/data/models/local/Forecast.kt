package ancic.karim.weatherancic.data.models.local

import ancic.karim.weatherancic.database.converters.DateConverter
import androidx.room.Entity
import androidx.room.TypeConverters
import java.time.LocalDate

@Entity(primaryKeys = ["date", "latitude", "longitude"])
@TypeConverters(DateConverter::class)
data class Forecast(
    val date: LocalDate,
    val latitude: Double,
    val longitude: Double,
    val temperatureNow: String?,
    val temperatureMin: String,
    val temperatureMax: String,
)