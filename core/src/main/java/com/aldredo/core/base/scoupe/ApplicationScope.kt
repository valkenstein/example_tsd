package com.aldredo.core.base.scoupe

import javax.inject.Qualifier
import javax.inject.Scope

@Scope
@Retention(value = AnnotationRetention.RUNTIME)
annotation class ApplicationScope

@Scope
@Retention(value = AnnotationRetention.RUNTIME)
annotation class AuthorizationScope



