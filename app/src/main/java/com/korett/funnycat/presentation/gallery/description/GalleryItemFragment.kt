package com.korett.funnycat.presentation.gallery.description

import android.content.res.TypedArray
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.korett.funnycat.databinding.FragmentGalleryItemBinding


class GalleryItemFragment : Fragment() {

    private var _binding: FragmentGalleryItemBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<GalleryItemFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryItemBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navController = findNavController()

        binding.btGallery.setOnClickListener {
            navController.navigateUp()
        }

        val a: TypedArray = requireContext().obtainStyledAttributes(
            TypedValue().data,
            intArrayOf(android.R.attr.colorAccent)
        )
        val placeholder = ColorDrawable(a.getColor(0, 0))
        a.recycle()

        Glide.with(requireContext())
            .load(args.imagePath)
            .transition(DrawableTransitionOptions.withCrossFade())
            .placeholder(placeholder)
            .into(binding.imCat)
    }

}