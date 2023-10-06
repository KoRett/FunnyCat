package com.korett.funnycat.presentation.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.korett.funnycat.databinding.FragmentGalleryItemBinding


class GalleryItemFragment : Fragment() {

    private var _binding: FragmentGalleryItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryItemBinding.inflate(inflater, container, false)
        val navController = findNavController()

        binding.btGallery.setOnClickListener {
            navController.navigateUp()
        }

        return binding.root
    }

}