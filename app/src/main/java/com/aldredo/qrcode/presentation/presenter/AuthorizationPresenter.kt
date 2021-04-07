package com.aldredo.qrcode.presentation.presenter

import com.aldredo.qrcode.data.model.stateRequest.ResponseStateAuthorization
import com.aldredo.qrcode.data.model.stateRequest.ResponseStateUpdateApp
import com.aldredo.qrcode.data.repository.AuthorizationRepository
import com.aldredo.qrcode.data.repository.UpdateRepository
import com.aldredo.qrcode.di.TaskWindowScope
import com.aldredo.qrcode.presentation.activity.AuthorizationView
import kotlinx.coroutines.*
import javax.inject.Inject

@TaskWindowScope
class AuthorizationPresenter @Inject constructor(
    private val repository: AuthorizationRepository,
    private val updateRepository: UpdateRepository
) {
    private var view: AuthorizationView? = null
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    fun tokenValidation(token: String) = scope.launch(Dispatchers.Main) {
        when (val result = tokenValidationAsync(token)) {
            ResponseStateAuthorization.EmptyResponse -> {
                view?.showMessageError("Ошибка Сервера")
            }
            is ResponseStateAuthorization.Error -> {
                view?.showMessageError("Ошибка авторизации - " + result.message)
            }
            is ResponseStateAuthorization.Result -> {
                view?.openFoyerActivity(result.result.menu)
            }
        }
    }

    fun checkUpdateVersionApp(currentVersionApp: Int) = scope.launch(Dispatchers.Main) {
        when (val newVersionUpdate = updateRepository.checkLastVersion()) {
            is ResponseStateUpdateApp.Result -> {
                val result = newVersionUpdate.result
                update(
                    result.link ?: "",
                    currentVersionApp,
                    result.last_version?.toInt() ?: 0
                )
            }
            is ResponseStateUpdateApp.Error -> {
                view?.showMessageError(newVersionUpdate.message.toString())
            }
        }
    }

    private fun update(url: String, currentVersionApp: Int, newVersion: Int) {
        if (currentVersionApp < newVersion) {
            view?.showMessageError(updateRepository.updateVersion(url))
        } else {
            view?.showMessageError("текущая версия актуальна")
        }
    }

    private suspend fun tokenValidationAsync(token: String) = withContext(Dispatchers.IO) {
        repository.tokenValidation(token)
    }

    fun attachView(view: AuthorizationView) {
        this.view = view
    }

    fun detachView() {
        scope.coroutineContext.cancelChildren()
    }
}