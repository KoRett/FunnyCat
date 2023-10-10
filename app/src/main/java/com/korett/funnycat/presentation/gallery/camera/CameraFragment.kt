package com.korett.funnycat.presentation.gallery.camera

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.FocusMeteringAction
import androidx.camera.core.ImageCapture
import androidx.camera.core.MeteringPointFactory
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceOrientedMeteringPointFactory
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.korett.funnycat.app.App
import com.korett.funnycat.databinding.FragmentCameraBinding
import com.korett.funnycat.domain.model.ErrorResultModel
import com.korett.funnycat.domain.model.PendingResultModel
import com.korett.funnycat.presentation.MainActivity
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider

class CameraFragment : Fragment() {
    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!

    private var imageCapture: ImageCapture? = null

    @Inject
    lateinit var vmFactory: Provider<CameraViewModel.Factory>
    private val vm: CameraViewModel by viewModels { vmFactory.get() }

    companion object {
        private const val AUTO_FOCUS_DURATION: Long = 2
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val camera = bindPreview(cameraProvider)
            setAutoFocus(camera)
            setFocusByTap(binding.previewView, camera)
        }, ContextCompat.getMainExecutor(requireContext()))

        binding.btGallery.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btTakePhoto.setOnClickListener {
            vm.capturePhoto(imageCapture!!)
        }

        vm.imageResult.observe(viewLifecycleOwner) { result ->
            binding.btTakePhoto.isEnabled = result !is PendingResultModel
            if (result is ErrorResultModel)
                Toast.makeText(requireContext(), "Error! Can't save the image!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).hideSystemUI()
    }

    override fun onPause() {
        super.onPause()
        (requireActivity() as MainActivity).showSystemUI()
    }

    private fun bindPreview(cameraProvider: ProcessCameraProvider): Camera {
        val preview = Preview.Builder()
            .build()

        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        preview.setSurfaceProvider(binding.previewView.surfaceProvider)

        imageCapture = ImageCapture.Builder().build()

        return cameraProvider.bindToLifecycle(
            viewLifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )
    }

    private fun setAutoFocus(camera: Camera) {
        val autoFocusPoint = SurfaceOrientedMeteringPointFactory(1f, 1f)
            .createPoint(.5f, .5f)
        val autoFocusAction = FocusMeteringAction.Builder(
            autoFocusPoint,
            FocusMeteringAction.FLAG_AF
        ).apply {
            setAutoCancelDuration(AUTO_FOCUS_DURATION, TimeUnit.SECONDS)
        }.build()
        camera.cameraControl.startFocusAndMetering(autoFocusAction)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setFocusByTap(previewView: PreviewView, camera: Camera) {
        previewView.setOnTouchListener { _, event ->
            return@setOnTouchListener when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    true
                }

                MotionEvent.ACTION_UP -> {
                    val factory: MeteringPointFactory = SurfaceOrientedMeteringPointFactory(
                        binding.previewView.width.toFloat(), previewView.height.toFloat()
                    )
                    val autoFocusPoint = factory.createPoint(event.x, event.y)
                    camera.cameraControl.startFocusAndMetering(
                        FocusMeteringAction.Builder(
                            autoFocusPoint,
                            FocusMeteringAction.FLAG_AF
                        ).apply {
                            disableAutoCancel()
                        }.build()
                    )
                    true
                }

                else -> false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}