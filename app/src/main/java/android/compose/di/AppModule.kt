package android.compose.di

import android.compose.data.AppDatabase
import android.compose.data.CarDao
import android.compose.data.remote.AutoMaatApi
import android.compose.data.remote.AutoMaatApi.Companion.BASE_URL
import android.compose.data.repository.auth.AuthRepository
import android.compose.data.repository.auth.IAuthRepository
import android.compose.data.local.AuthPreferences
import android.compose.data.repository.cars.CarsRepository
import android.compose.data.repository.cars.CarsRepositoryImplementation
import android.compose.util.Constants.AUTH_PREFERENCES
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_store")
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
    fun provideAuthPreferences(@ApplicationContext context: Context) =
        AuthPreferences(context)


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
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "database-name"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideCarDao(database: AppDatabase): CarDao {
        return database.carDao()
    }

    @Provides
    @Singleton
    fun provideCarsRepositoryImplementation(
        autoMaatApi: AutoMaatApi,
        carDao: CarDao
    ): CarsRepositoryImplementation {
        return CarsRepositoryImplementation(
            autoMaatApi = autoMaatApi,
            carDao = carDao)
    }
}
