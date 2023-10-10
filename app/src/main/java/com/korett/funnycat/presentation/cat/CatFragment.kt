package com.korett.funnycat.presentation.cat

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.korett.funnycat.R
import com.korett.funnycat.app.App
import com.korett.funnycat.databinding.FragmentCatBinding
import com.korett.funnycat.model.renderedSimpleResult
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navController = findNavController()

        binding.btSettings.setOnClickListener {
            navController.navigate(R.id.action_catFragment_to_settingsFragment)
        }

        binding.btGallery.setOnClickListener {
            navController.navigate(R.id.action_catFragment_to_galleryFragment)
        }

        binding.btLike.setOnClickListener {
            vm.saveCat(System.currentTimeMillis())
            vm.getCat()
        }

        binding.btDislike.setOnClickListener {
            vm.getCat()
        }

        loadCat()
    }

    private fun loadCat() {
        vm.catsResult.observe(viewLifecycleOwner) { result ->
            renderedSimpleResult(binding.cardCatConstraint, result, onSuccess = {
                val a: TypedArray = requireContext().obtainStyledAttributes(
                    TypedValue().data,
                    intArrayOf(android.R.attr.colorAccent)
                )
                val placeholder = ColorDrawable(a.getColor(0, 0))
                a.recycle()

                Glide.with(requireContext())
                    .load(it[0].imagePath)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .placeholder(placeholder)
                    .into(binding.imCat)
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}