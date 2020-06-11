package org.jdc.template.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import timber.log.Timber

abstract class BaseFragment : Fragment() {

    private val baseViewModel: BaseFragmentViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        baseViewModel.checkParam("Foo")
    }

    @MainThread
    protected inline fun <T> LiveData<T>.observe(crossinline onChanged: (T?) -> Unit): Observer<T> {
        val wrappedObserver = Observer<T> { value ->
            onChanged.invoke(value)
        }
        observe(viewLifecycleOwner, wrappedObserver)
        return wrappedObserver
    }

    @MainThread
    protected inline fun <T : Any> LiveData<T>.observeKt(crossinline onChanged: (T) -> Unit): Observer<T> {
        val wrappedObserver = Observer<T> { value ->
            if (value != null) {
                onChanged.invoke(value)
            } else {
                Timber.w("Cannot post null value to a non-null LiveData")
            }
        }
        observe(viewLifecycleOwner, wrappedObserver)
        return wrappedObserver
    }

    @MainThread
    protected inline fun <T> LiveData<T?>.observeNotNull(crossinline onChanged: (T) -> Unit): Observer<T?> {
        val wrappedObserver = Observer<T?> { value ->
            if (value != null) {
                onChanged.invoke(value)
            }
        }
        observe(viewLifecycleOwner, wrappedObserver)
        return wrappedObserver
    }

    @MainThread
    protected inline fun <T> LiveData<T>.observeByFragment(crossinline onChanged: (T?) -> Unit): Observer<T> {
        val wrappedObserver = Observer<T> { value -> onChanged.invoke(value) }
        observe(viewLifecycleOwner, wrappedObserver)
        return wrappedObserver
    }

    @MainThread
    protected inline fun <T : Any> LiveData<T>.observeByFragmentKt(crossinline onChanged: (T) -> Unit): Observer<T> {
        val wrappedObserver = Observer<T> { value ->
            if (value != null) {
                onChanged.invoke(value)
            } else {
                Timber.i("Cannot post null value to a non-null LiveData")
            }
        }
        observe(viewLifecycleOwner, wrappedObserver)
        return wrappedObserver
    }

    @MainThread
    protected inline fun <T> LiveData<T?>.observeNotNullByFragment(crossinline onChanged: (T) -> Unit): Observer<T?> {
        val wrappedObserver = Observer<T?> { value ->
            if (value != null) {
                onChanged.invoke(value)
            }
        }
        observe(viewLifecycleOwner, wrappedObserver)
        return wrappedObserver
    }
}

class BaseFragmentViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    fun checkParam(key: String) {
        if (savedStateHandle.contains(key)) {
            Timber.i("SavedStateHandle[$key] = ${savedStateHandle.get<String>(key)}")
        } else {
            Timber.i("$key Missing")
        }
    }
}
