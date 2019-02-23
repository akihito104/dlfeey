package com.freshdigitable.dlfeey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.MapKey
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

class ViewModelProviderFactory @Inject constructor(
    private val providers: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val provider: Provider<out ViewModel> =
            providers[modelClass] ?: find(modelClass) ?: throw IllegalStateException("unregistered class: $modelClass")
        @Suppress("UNCHECKED_CAST")
        return provider.get() as T
    }

    private fun <T : ViewModel> find(modelClass: Class<T>): Provider<out ViewModel>? {
        return providers.entries
            .firstOrNull { (k, _) ->
                modelClass.isAssignableFrom(k)
            }?.value
    }
}

@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)
