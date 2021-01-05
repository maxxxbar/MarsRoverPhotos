package ws.worldshine.marsroverphotos.di.modules

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import ws.worldshine.marsroverphotos.di.factories.ViewModelBuilder
import ws.worldshine.marsroverphotos.di.factories.ViewModelKey
import ws.worldshine.marsroverphotos.ui.listfragment.ListFragment
import ws.worldshine.marsroverphotos.ui.listfragment.ListFragmentViewModel

@ExperimentalPagingApi
@Module
abstract class ListFragmentModule {

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun listFragment(): ListFragment

    @Binds
    @IntoMap
    @ViewModelKey(ListFragmentViewModel::class)
    abstract fun bindViewModel(viewModel: ListFragmentViewModel): ViewModel

}