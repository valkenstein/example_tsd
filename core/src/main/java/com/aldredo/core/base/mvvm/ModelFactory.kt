package com.aldredo.core.base.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory

class ModelFactory(private val model: ViewModel) : NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>) = model as T
}