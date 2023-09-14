package com.korett.funnycat.presentation.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.korett.funnycat.R
import com.korett.funnycat.app.App
import com.korett.funnycat.databinding.FragmentCatBinding
import javax.inject.Inject
import javax.inject.Provider

class CatFragment : Fragment() {

    private var _binding: FragmentCatBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var vmFactory: Provider<CatViewModel.Factory>
    private val vm: CatViewModel by viewModels { vmFactory.get() }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatBinding.inflate(inflater, container, false)
        val navController = findNavController()

        binding.btSettings.setOnClickListener {
            navController.navigate(R.id.action_catFragment_to_settingsFragment)
        }

        binding.btGallery.setOnClickListener {
            navController.navigate(R.id.action_catFragment_to_galleryFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}