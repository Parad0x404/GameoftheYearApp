package com.dicoding.gameoftheyearapp.di

import com.dicoding.gameoftheyearapp.data.GameRepository

object Injection {
    fun provideRepository(): GameRepository {
        return GameRepository.getInstance()
    }
}