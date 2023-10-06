package com.korett.funnycat.model

import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.korett.funnycat.R
import com.korett.funnycat.databinding.PartResultBinding
import com.korett.funnycat.domain.model.ErrorResultModel
import com.korett.funnycat.domain.model.PendingResultModel
import com.korett.funnycat.domain.model.ResultModel
import com.korett.funnycat.domain.model.SuccessResultModel

fun <T> Fragment.renderResult(
    root: ViewGroup, resultModel: ResultModel<T>,
    onPending: () -> Unit,
    onError: (Exception) -> Unit,
    onSuccess: (T) -> Unit
) {
    root.children.forEach { it.visibility = View.GONE }
    when (resultModel) {
        is SuccessResultModel -> onSuccess(resultModel.data)
        is ErrorResultModel -> onError(resultModel.exception)
        is PendingResultModel -> onPending()
        else -> {}
    }
}

fun <T> Fragment.renderedSimpleResult(
    root: ViewGroup,
    resultModel: ResultModel<T>,
    onSuccess: (T) -> Unit
) {
    val binding = PartResultBinding.bind(root)
    renderResult(
        root = root,
        resultModel = resultModel,
        onPending = {
            binding.progressBar.visibility = View.VISIBLE
        },
        onError = {
            binding.errorContainer.visibility = View.VISIBLE
        },
        onSuccess = { successData ->
            root.children.filter { it.id != R.id.progress_bar && it.id != R.id.error_container }
                .forEach { it.visibility = View.VISIBLE }
            onSuccess(successData)
        }
    )
}

fun Fragment.findTopNavController(): NavController {
    val topLevelHost =
        requireActivity().supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment?
    return topLevelHost?.navController ?: findNavController()
}