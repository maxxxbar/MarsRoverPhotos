package ws.worldshine.marsroverphotos.di.components

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import ws.worldshine.marsroverphotos.App
import ws.worldshine.marsroverphotos.di.modules.ListFragmentModule
import ws.worldshine.marsroverphotos.di.modules.NetworkModule
import ws.worldshine.marsroverphotos.di.modules.RoomModule
import javax.inject.Singleton

@ExperimentalPagingApi
@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        RoomModule::class,
        NetworkModule::class,
        ListFragmentModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

}