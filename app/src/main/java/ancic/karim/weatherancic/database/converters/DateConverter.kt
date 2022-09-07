package ancic.karim.weatherancic.database.converters

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

object DateConverter {
    @TypeConverter
    fun toDate(epochMilli: Long): LocalDate = Instant.ofEpochMilli(epochMilli).atZone(ZoneId.systemDefault()).toLocalDate()

    @TypeConverter
    fun toEpochMilli(localDate: LocalDate): Long = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
}