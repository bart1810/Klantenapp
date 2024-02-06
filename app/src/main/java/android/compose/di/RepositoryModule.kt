package android.compose.di

import android.compose.data.repository.cars.CarsRepository
import android.compose.data.repository.cars.CarsRepositoryImplementation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    @Named("carsRepo")
    abstract fun bindCarsRepository(
        carsRepositoryImplementation: CarsRepositoryImplementation
    ): CarsRepository

}