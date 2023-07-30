package com.example.game15.di

import com.example.game15.repository.Repository
import org.koin.dsl.module


val repositoryModule = module {
    single { Repository() }
}