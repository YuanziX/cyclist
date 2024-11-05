package dev.yuanzix.cyclist.di

import dev.yuanzix.cyclist.auth.data.networking.RemoteAuthRepository
import dev.yuanzix.cyclist.auth.domain.AuthRepository
import dev.yuanzix.cyclist.auth.presentation.login.LoginViewModel
import dev.yuanzix.cyclist.auth.presentation.signup.SignupViewModel
import dev.yuanzix.cyclist.core.data.UserPreferencesDataStore
import dev.yuanzix.cyclist.core.data.networking.HttpClientFactory
import dev.yuanzix.cyclist.core.presentation.start.StartViewModel
import dev.yuanzix.cyclist.dash.data.RemoteBicycleDataSource
import dev.yuanzix.cyclist.dash.domain.BicycleDataSource
import dev.yuanzix.cyclist.dash.presentation.bicycle_details.BicycleDetailsViewModel
import dev.yuanzix.cyclist.dash.presentation.home.HomeViewModel
import dev.yuanzix.cyclist.dash.presentation.mycycle.MyCycleViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single {
        HttpClientFactory.create(CIO.create())
    }
    singleOf(::UserPreferencesDataStore)
    singleOf(::RemoteAuthRepository).bind<AuthRepository>()
    singleOf(::RemoteBicycleDataSource).bind<BicycleDataSource>()

    viewModelOf(::StartViewModel)

    viewModelOf(::LoginViewModel)
    viewModelOf(::SignupViewModel)

    viewModelOf(::HomeViewModel)
    factory { (bikeId: String) -> BicycleDetailsViewModel(bikeId, get(), get()) }

    viewModelOf(::MyCycleViewModel)
}