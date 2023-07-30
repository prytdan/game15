package com.example.game15.di

import com.example.game15.viewviewmodel.activities.MainViewModel
import com.example.game15.viewviewmodel.fragments.game.GameViewModel
import com.example.game15.viewviewmodel.fragments.menu.MenuViewModel
import com.example.game15.viewviewmodel.fragments.rules.RulesViewModel
import com.example.game15.viewviewmodel.fragments.start.StartViewModel
import com.example.game15.viewviewmodel.fragments.webview.WebViewViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.util.Random

val appModule = module {
    single { Random() }
    viewModel { MainViewModel(repository = get()) }
    viewModel { StartViewModel() }
    viewModel { MenuViewModel() }
    viewModel { GameViewModel(random = get()) }
    viewModel { RulesViewModel() }
    viewModel { WebViewViewModel() }
}