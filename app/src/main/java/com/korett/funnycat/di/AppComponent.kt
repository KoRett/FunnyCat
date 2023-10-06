package com.korett.funnycat.di

import android.content.Context
import com.korett.funnycat.presentation.MainActivity
import com.korett.funnycat.presentation.cat.CatFragment
import com.korett.funnycat.presentation.gallery.GalleryFragment
import com.korett.funnycat.presentation.gallery.camera.CameraFragment
import com.korett.funnycat.presentation.settings.SettingsFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        DataModule::class,
        DataBindModule::class
    ]
)
@Singleton
interface AppComponent {

    fun inject(catFragment: CatFragment)
    fun inject(mainActivity: MainActivity)
    fun inject(settingsFragment: SettingsFragment)
    fun inject(cameraFragment: CameraFragment)
    fun inject(galleryFragment: GalleryFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }
}