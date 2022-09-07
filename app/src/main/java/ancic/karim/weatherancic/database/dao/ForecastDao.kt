package ancic.karim.weatherancic.database.dao

import ancic.karim.weatherancic.data.models.local.Forecast
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ForecastDao {
    @Query("SELECT * FROM forecast WHERE ABS(latitude - :latitude) < :delta AND ABS(longitude - :longitude) < :delta")
    fun load(latitude: Double, longitude: Double, delta: Double = 0.5): Flow<List<Forecast>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(forecast: List<Forecast>)

    @Query("DELETE FROM forecast")
    fun deleteAll()
}
