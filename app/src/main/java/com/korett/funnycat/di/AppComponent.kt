package com.korett.funnycat.di

import android.content.Context
import com.korett.funnycat.presentation.main.CatFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component
@Singleton
interface AppComponent {

    fun inject(catFragment: CatFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }
}