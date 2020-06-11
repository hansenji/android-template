package org.jdc.template.inject

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.jdc.template.Analytics
import org.jdc.template.BuildConfig
import org.jdc.template.model.webservice.ServiceModule
import org.jdc.template.prefs.Prefs
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module(includes = [ServiceModule::class])
class AppModule {

    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json(
            JsonConfiguration.Stable.copy(
                ignoreUnknownKeys = true,
                serializeSpecialFloatingPointValues = true,
                useArrayPolymorphism = true
            )
        )
    }

    @Provides
    @Singleton
    fun provideAnalytics(@ApplicationContext context: Context, prefs: Prefs): Analytics {
        // Only send analytics with versions of the app that are NOT debuggable (such as BETA or RELEASE)
        if (BuildConfig.DEBUG) {
            return Analytics.DebugAnalytics()
        }

        val firebaseAnalytics = FirebaseAnalytics.getInstance(context).apply {
            setUserId(prefs.getAppInstanceId())
        }
        return Analytics.FirebaseAnalyticsWrapper(firebaseAnalytics)
    }
}
