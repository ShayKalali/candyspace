package com.example.candyspace.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.candyspace.di.scope.ApplicationScope
import javax.inject.Inject
import javax.inject.Provider

@ApplicationScope
class ViewModelProviderFactory @Inject constructor(private val creators: MutableMap<Class<out ViewModel>, Provider<ViewModel>>?) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var creator: Provider<out ViewModel>? = creators?.get(modelClass)
        if (creator == null) {
            for (entry in creators?.entries!!) {
                if (modelClass.isAssignableFrom(entry.key)) {
                    creator = entry.value
                    break
                }
            }
        }
        if (creator == null) {
            throw IllegalArgumentException("unknown model class $modelClass")
        }
        try {
            @Suppress("UNCHECKED_CAST")
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}