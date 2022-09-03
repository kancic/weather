package ancic.karim.weatherancic

import android.app.Application
import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val location = MutableLiveData<Location>()
    val city = MutableLiveData<String?>()

    init {
        location.asFlow().filterNotNull().map {
            Geocoder(application).getFromLocation(it.latitude, it.longitude, 1)
        }.map { it.firstOrNull() }.flowOn(Dispatchers.IO).filterNotNull().onEach {
            city.value = it.locality
        }.launchIn(viewModelScope)
    }
}