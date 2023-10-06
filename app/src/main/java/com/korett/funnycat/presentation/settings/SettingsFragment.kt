package com.korett.funnycat.presentation.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.korett.funnycat.R
import com.korett.funnycat.app.App
import com.korett.funnycat.databinding.FragmentSettingsBinding
import com.korett.funnycat.domain.model.Theme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import javax.inject.Provider

class SettingsFragment : Fragment() {

    @Inject
    lateinit var vmFactory: Provider<SettingsViewModel.Factory>
    private val vm: SettingsViewModel by viewModels { vmFactory.get() }

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        binding.btMain.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.toggleButtonTheme.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.bt_dark_theme -> setTheme(Theme.Dark)
                    R.id.bt_system_theme -> setTheme(Theme.System)
                    R.id.bt_light_theme -> setTheme(Theme.Light)
                }
            }
        }

        setCurrentActiveThemeButton()

        return binding.root
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun setCurrentActiveThemeButton() {
        val result = vm.getTheme()
        result.invokeOnCompletion { throwable ->
            if (throwable == null) {
                val id = when (result.getCompleted()) {
                    Theme.Dark -> R.id.bt_dark_theme
                    Theme.System -> R.id.bt_system_theme
                    Theme.Light -> R.id.bt_light_theme
                }
                binding.toggleButtonTheme.check(id)
            }
        }
    }

    private fun setTheme(theme: Theme) {
        AppCompatDelegate.setDefaultNightMode(theme.value)
        vm.saveTheme(theme)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}