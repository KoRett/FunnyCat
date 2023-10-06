package com.korett.funnycat.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.korett.funnycat.domain.model.ResultModel

typealias LiveResult<T> = LiveData<ResultModel<T>>
typealias MutableLiveResult<T> = MutableLiveData<ResultModel<T>>
typealias MediatorLiveResult<T> = MediatorLiveData<ResultModel<T>>