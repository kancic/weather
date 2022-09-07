package ancic.karim.weatherancic.database.converters

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

object TimeConverter {
    @TypeConverter
    fun toTime(epochMilli: Long): LocalDateTime = Instant.ofEpochMilli(epochMilli).atZone(ZoneId.systemDefault()).toLocalDateTime()

    @TypeConverter
    fun toEpochMilli(localDateTime: LocalDateTime): Long = localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli()
}