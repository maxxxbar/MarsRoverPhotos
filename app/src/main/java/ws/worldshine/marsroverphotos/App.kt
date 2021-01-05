package ws.worldshine.marsroverphotos

import androidx.paging.ExperimentalPagingApi
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import ws.worldshine.marsroverphotos.di.components.DaggerAppComponent

@ExperimentalPagingApi
class App: DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
       return DaggerAppComponent.factory().create(applicationContext)
    }

}