package ancic.karim.weatherancic.data.models.local

import ancic.karim.weatherancic.database.converters.DateConverter
import ancic.karim.weatherancic.database.converters.TimeConverter
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.TypeConverters
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(primaryKeys = ["date", "latitude", "longitude"])
@TypeConverters(DateConverter::class)
data class Forecast(
    val date: LocalDate,
    val latitude: Double,
    val longitude: Double,
    val temperatureNow: String?,
    val temperatureMin: String,
    val temperatureMax: String,
    @Embedded
    val precipitation: Precipitation?
)

@TypeConverters(TimeConverter::class)
class Precipitation(
    val amount: Double,
    val time: LocalDateTime,
    val unit: String
)
