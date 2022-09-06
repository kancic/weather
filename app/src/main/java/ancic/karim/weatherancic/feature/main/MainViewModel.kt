package ancic.karim.weatherancic.feature.main

import android.app.Application
import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(application: Application, repository: MainRepository) : ViewModel() {
    val location = MutableLiveData<Location?>()
    val city = MutableLiveData<String?>()
    val forecast = location.asFlow().filterNotNull().distinctUntilChanged().flatMapConcat {
        repository.getForecast(it)
    }.asLiveData()

    init {
        location.asFlow().filterNotNull().distinctUntilChanged().map {
            Geocoder(application).getFromLocation(it.latitude, it.longitude, 1)
        }.map { it.firstOrNull() }.flowOn(Dispatchers.IO).filterNotNull().onEach {
            city.value = it.locality
        }.launchIn(viewModelScope)
    }
}