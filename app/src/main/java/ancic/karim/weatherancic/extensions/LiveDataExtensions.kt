package ancic.karim.weatherancic.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

fun <T> LiveData<T?>.nonNull(): LiveData<T> = MediatorLiveData<T>().also { mediatorLiveData ->
    mediatorLiveData.addSource(this) { value: T? ->
        if (value != null) {
            mediatorLiveData.value = value
        }
    }
}