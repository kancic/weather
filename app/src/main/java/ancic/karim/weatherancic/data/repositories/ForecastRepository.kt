package ancic.karim.weatherancic.data.repositories

import ancic.karim.weatherancic.data.models.converters.ForecastConverter
import ancic.karim.weatherancic.data.models.local.Forecast
import ancic.karim.weatherancic.data.repositories.sources.local.ForecastLocalDataSource
import ancic.karim.weatherancic.data.repositories.sources.remote.ForecastRemoteDataSource
import android.location.Location
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class ForecastRepository @Inject constructor(
    private val localDataSource: ForecastLocalDataSource,
    private val remoteDataSource: ForecastRemoteDataSource,
    private val forecastConverter: ForecastConverter
) {
    fun getForecast(location: Location): Flow<Result<List<Forecast>>> = flow {
        remoteDataSource.getForecast(location).let {
            forecastConverter.map(it)
        }.also {
            localDataSource.save(it)
        }
        emitAll(localDataSource.load(location).map { Result.success(it) })
    }.catch {
        emit(Result.failure(it))
        emitAll(localDataSource.load(location).map { Result.success(it) })
    }.flowOn(Dispatchers.IO)
}