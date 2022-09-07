package ancic.karim.weatherancic.utils.preferences

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesUtil @Inject constructor(private val sharedPreferences: SharedPreferences) {
    private val KEY_CHECK_FORECAST_IN_BACKGROUND: String = "KEY_CHECK_FORECAST_IN_BACKGROUND"

    fun getCheckForecastInBackground() = sharedPreferences.getBoolean(KEY_CHECK_FORECAST_IN_BACKGROUND, false)

    fun setCheckForecastInBackground(check: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_CHECK_FORECAST_IN_BACKGROUND, check).apply()
    }
}