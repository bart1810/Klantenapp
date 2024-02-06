package android.compose.data

import android.compose.data.local.CarEntity
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CarDao {
    @Query("SELECT * FROM cars")
    fun getAllCars(): Flow<List<CarEntity>>

    @Query("SELECT * FROM cars WHERE id = :carId")
    fun getCarById(carId: Int): Flow<CarEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(car: CarEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cars: List<CarEntity>)

    @Query("DELETE FROM cars")
    suspend fun deleteAllCars()

    @Query("DELETE FROM cars WHERE id = :carId")
    suspend fun deleteCarById(carId: Int)
}