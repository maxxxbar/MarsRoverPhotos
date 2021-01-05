package ws.worldshine.marsroverphotos.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ws.worldshine.marsroverphotos.database.Database
import ws.worldshine.marsroverphotos.database.NasaDao
import ws.worldshine.marsroverphotos.database.RemoteKeysDao
import javax.inject.Singleton

@Module
object RoomModule {

    @Singleton
    @Provides
    fun provideNasaDatabase(context: Context): Database {
        return Room.databaseBuilder(
            context.applicationContext,
            Database::class.java,
            "database.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideDao(database: Database): NasaDao {
        return database.nasaDao()
    }

    @Singleton
    @Provides
    fun provideRemoteKeysDao(database: Database): RemoteKeysDao {
        return database.remoteKeysDao()
    }
}