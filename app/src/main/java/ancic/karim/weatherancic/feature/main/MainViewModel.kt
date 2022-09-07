package ancic.karim.weatherancic.feature.main

import ancic.karim.weatherancic.data.repositories.ForecastRepository
import android.app.Application
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(application: Application, repository: ForecastRepository) : ViewModel() {
    private val geocoder = Geocoder(application)
    private val addressSuggestions = MutableLiveData<List<Address>>()

    val location = MutableLiveData<Location?>()
    val city = MutableLiveData<String?>()
    val citySuggestions = addressSuggestions.map { it.map { it.locality }.filterNotNull() }
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

    fun searchCity(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            addressSuggestions.postValue(if (city.isNotEmpty()) {
                try {
                    geocoder.getFromLocationName(city, 4).filter { it.locality?.contains(city, ignoreCase = true) == true }
                } catch (e: Exception) {
                    emptyList()
                }
            } else {
                emptyList()
            })
        }
    }

    fun selectSuggestion(position: Int) {
        location.value = addressSuggestions.value?.get(position)?.let { address ->
            Location(LocationManager.PASSIVE_PROVIDER).apply {
                latitude = address.latitude
                longitude = address.longitude
            }
        }
    }
}