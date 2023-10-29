package com.korett.funnycat.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.korett.funnycat.app.App
import com.korett.funnycat.databinding.ActivityMainBinding
import com.korett.funnycat.model.NotificationWorker
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.time.Duration
import javax.inject.Inject
import javax.inject.Provider


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var vmFactory: Provider<MainViewModel.Factory>
    private val vm: MainViewModel by viewModels { vmFactory.get() }

    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val IMMERSIVE_FLAG_TIMEOUT = 250L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as App).appComponent.inject(this)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setCurrentActiveTheme()

        setContentView(binding.root)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun setCurrentActiveTheme() {
        val result = vm.getTheme()
        result.invokeOnCompletion { throwable ->
            if (throwable == null) {
                AppCompatDelegate.setDefaultNightMode(result.getCompleted().value)
            }
        }
    }

    fun hideSystemUI() {
        binding.mainNavHostFragment.postDelayed({
            WindowCompat.setDecorFitsSystemWindows(window, false)
            WindowInsetsControllerCompat(window, binding.mainNavHostFragment).let { controller ->
                controller.hide(WindowInsetsCompat.Type.systemBars())
                controller.systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }, IMMERSIVE_FLAG_TIMEOUT)
    }

    fun showSystemUI() {
        binding.mainNavHostFragment.postDelayed({
            WindowCompat.setDecorFitsSystemWindows(window, true)
            WindowInsetsControllerCompat(window, binding.mainNavHostFragment).let { controller ->
                controller.show(WindowInsetsCompat.Type.systemBars())
                controller.systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_BARS_BY_TOUCH
            }
        }, IMMERSIVE_FLAG_TIMEOUT)
    }

    override fun onPause() {
        super.onPause()
        createNotificationWork()
    }

    private fun createNotificationWork() {
        val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(Duration.ofSeconds(5))
            .build()
        WorkManager.getInstance(applicationContext).enqueue(workRequest)
    }

}