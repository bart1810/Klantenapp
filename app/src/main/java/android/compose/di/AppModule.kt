package android.compose.di

import android.compose.data.remote.AutoMaatApi
import android.compose.data.remote.AutoMaatApi.Companion.BASE_URL
import android.compose.data.repository.auth.AuthRepository
import android.compose.data.repository.auth.IAuthRepository
import android.compose.data.local.AuthPreferences
import android.compose.domain.use_cases.LoginUseCase
import android.compose.util.Constants.AUTH_PREFERENCES
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePreferenceDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = {
                context.preferencesDataStoreFile(AUTH_PREFERENCES)
            }
        )

    @Provides
    @Singleton
    fun provideAuthPreferences(dataStore: DataStore<Preferences>) =
        AuthPreferences(dataStore)


    @Provides
    @Singleton
    fun providesApiService(): AutoMaatApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AutoMaatApi::class.java)
    }

    @Provides
    @Singleton
    fun providesAuthRepository(
        apiService: AutoMaatApi,
        preferences: AuthPreferences
    ): AuthRepository {
        return IAuthRepository(
            autoMaatApi = apiService,
            preferences = preferences
        )
    }

    @Provides
    @Singleton
    fun providesLoginUseCase(repository: AuthRepository): LoginUseCase {
        return LoginUseCase(repository)
    }


//    @Provides
//    @Singleton
//    fun providesRegisterUseCase(repository: AuthRepository): RegisterUseCase {
//        return RegisterUseCase(repository)
//    }
}