package ancic.karim.weatherancic.database

import ancic.karim.weatherancic.data.models.local.Forecast
import ancic.karim.weatherancic.database.dao.ForecastDao
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Forecast::class], version = 2, autoMigrations = [AutoMigration(from = 1, to = 2)])
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun forecastDao(): ForecastDao
}