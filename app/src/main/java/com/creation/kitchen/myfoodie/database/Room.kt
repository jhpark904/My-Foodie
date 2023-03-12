package com.creation.kitchen.myfoodie.database

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {

    @Query("SELECT * FROM DatabaseMeal")
    fun getMeals(): Flow<List<DatabaseMeal>>

    @Query("SELECT EXISTS(SELECT 1 FROM DatabaseMeal WHERE id = :id LIMIT 1)")
    fun isSaved(id: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(meal: DatabaseMeal)

    @Delete
    fun delete(meal: DatabaseMeal)
}

@Database(entities = [DatabaseMeal::class], version = 1)
abstract class MealDatabase: RoomDatabase() {
    abstract fun mealDao(): MealDao
}

private lateinit var INSTANCE: MealDatabase

fun getDatabase(context: Context): MealDatabase {
    synchronized(MealDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                MealDatabase::class.java, "meals"
            ).build()
        }
        return INSTANCE
    }
}

