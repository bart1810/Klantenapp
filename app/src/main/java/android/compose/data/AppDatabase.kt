package android.compose.data

import android.compose.data.local.CarEntity
import androidx.room.Database
import androidx.room.RoomDatabase
import android.compose.data.CarDao

@Database(entities = [CarEntity::class], version=4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun carDao(): CarDao
}