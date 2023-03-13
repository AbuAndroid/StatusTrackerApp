package com.example.statustracker.di.module

import com.example.statustracker.manager.DataStoreManager
import com.example.statustracker.repository.WorkStatusRepository
import com.example.statustracker.ui.homescreen.HomeScreenViewModel
import com.example.statustracker.ui.loginScreen.LoginScreen
import com.example.statustracker.ui.loginScreen.LoginViewModel
import com.example.statustracker.utils.Networkhelper
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object Test {
    fun modules() = repositoryModule + viewModelModule +commonModule
}

val repositoryModule = module {
    single {
        WorkStatusRepository(get())
    }
}

val viewModelModule = module {

    viewModel{
        HomeScreenViewModel(get(),get(),get())
    }
    viewModel {
        LoginViewModel()
    }
}

val commonModule = module {
    single {
        Networkhelper(androidContext())
    }
    single {
        RestHelper.client
    }
    single {
        DataStoreManager(androidContext())
    }
}