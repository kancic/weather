package ancic.karim.weatherancic.utils.preferences

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PreferencesModule {
    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context) = context.getSharedPreferences("weather", Context.MODE_PRIVATE)
}