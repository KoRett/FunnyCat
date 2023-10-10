package com.korett.funnycat.presentation.gallery

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.korett.funnycat.R
import com.korett.funnycat.app.App
import com.korett.funnycat.databinding.FragmentGalleryBinding
import com.korett.funnycat.model.renderedSimpleResult
import javax.inject.Inject
import javax.inject.Provider


class GalleryFragment : Fragment() {
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val CAMERA_PERMISSION_CODE = 1
    }

    @Inject
    lateinit var vmFactory: Provider<GalleryViewModel.Factory>
    private val vm: GalleryViewModel by viewModels { vmFactory.get() }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navController = findNavController()

        binding.btMain.setOnClickListener {
            navController.popBackStack()
        }

        binding.btCamera.setOnClickListener {
            if (checkCameraPermission()) {
                navController.navigate(R.id.action_galleryFragment_to_cameraFragment)
            } else {
                requestRuntimePermission()
            }
        }

        vm.catsResult.observe(viewLifecycleOwner) { result ->
            renderedSimpleResult(binding.constraintCats, result, onSuccess = { data ->
                val adapter = GalleryAdapter(data)
                adapter.setOnItemClickListener(object : GalleryAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        val galleryItemDestination =
                            GalleryFragmentDirections.actionGalleryFragmentToGalleryItemFragment(
                                data[position].imagePath
                            )
                        navController.navigate(galleryItemDestination)
                    }
                })
                binding.recyclerviewCats.adapter = adapter
            })
        }

    }

    override fun onStart() {
        super.onStart()
        vm.getCat()
    }

    private fun checkCameraPermission(): Boolean = ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED

    private fun requestRuntimePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.CAMERA
            )
        ) {
            AlertDialog.Builder(context)
                .setMessage("This application requires CAMERA permission for particular feature to work as expected.")
                .setTitle("Permission required")
                .setCancelable(false)
                .setPositiveButton("Ok") { dialog, _ ->
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.CAMERA),
                        CAMERA_PERMISSION_CODE
                    )
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_CODE
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}