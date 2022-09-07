package ancic.karim.weatherancic.worker

import ancic.karim.weatherancic.R
import ancic.karim.weatherancic.data.models.local.Precipitation
import ancic.karim.weatherancic.data.repositories.ForecastRepository
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.firstOrNull
import java.time.LocalDateTime

@HiltWorker
class ForecastWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val forecastRepository: ForecastRepository
) : CoroutineWorker(context, workerParameters) {
    companion object {
        private const val CHANNEL_ID = "CHANNEL_ID"
    }

    override suspend fun doWork(): Result {
        val result = try {
            forecastRepository.getForecastFromLastLocation().firstOrNull()
        } catch (e: Exception) {
            null
        }
        return if (result?.isSuccess == true && result.getOrNull() != null) {
            val now = LocalDateTime.now()
            result.getOrNull()?.find { it.date == now.toLocalDate() }?.precipitation?.let {
                val twoHoursFromNow = now.plusHours(2)
                if (it.time.isAfter(now) && it.time.isBefore(twoHoursFromNow)) {
                    showBadWeatherNotification(it)
                }
            }
            Result.success()
        } else {
            Result.failure()
        }
    }

    private fun showBadWeatherNotification(precipitation: Precipitation) {
        createNotificationChannel()
        NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(applicationContext.getString(R.string.app_name))
            .setContentText(applicationContext.getString(R.string.notification_bad_weather, precipitation.time.toLocalTime().toString()))
            .setAutoCancel(true)
            .build().let {
                NotificationManagerCompat.from(applicationContext).notify(precipitation.hashCode(), it)
            }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = applicationContext.getString(R.string.app_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            val notificationManager: NotificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}