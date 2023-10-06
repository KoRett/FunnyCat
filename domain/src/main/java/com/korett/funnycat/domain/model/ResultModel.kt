package com.korett.funnycat.domain.model


typealias Mapper<Input, Output> = (Input) -> Output

sealed class ResultModel<T> {

    fun <R> map(mapper: Mapper<T, R>? = null) = when (this) {
        is PendingResultModel -> PendingResultModel()
        is ErrorResultModel -> ErrorResultModel(this.exception)
        is SuccessResultModel -> {
            if (mapper == null) throw IllegalArgumentException("Mapper should not be NULL for success result")
            SuccessResultModel(mapper(this.data))
        }

        is NothingResultModel -> NothingResultModel()
    }

}

class NothingResultModel<T> : ResultModel<T>()

class PendingResultModel<T> : ResultModel<T>()

class SuccessResultModel<T>(
    val data: T
) : ResultModel<T>()

class ErrorResultModel<T>(
    val exception: Exception
) : ResultModel<T>()

fun <T> ResultModel<T>?.takeSuccess(): T? {
    return if (this is SuccessResultModel)
        this.data
    else
        null
}