package com.inderbagga.foundation.ui.detail

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;

/**
 * Created by Inder Bagga on 19/06/20.
 * Email er[dot]inderbagga[at]gmail[dot]com
 */
class DetailViewModel() : ViewModel() {

    val isLoading: ObservableField<Boolean> = ObservableField()
    val errorMessage: ObservableField<String> = ObservableField()
    val pageUrl: MutableLiveData<String> = MutableLiveData()

    init {
        isLoading.set(true)
        errorMessage.set(null)
    }

    fun setup(){
        isLoading.set(false)
    }

    fun openInBrowser(pageUrl: String?) {
        this.pageUrl.value = pageUrl
    }
}
