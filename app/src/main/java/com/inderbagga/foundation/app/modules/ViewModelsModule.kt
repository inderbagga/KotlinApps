package com.inderbagga.foundation.app.modules

import com.inderbagga.foundation.ui.detail.DetailViewModel
import com.inderbagga.foundation.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Inder Bagga on 20/06/20.
 * Email er[dot]inderbagga[at]gmail[dot]com
 */
val mainViewModel = module {
    viewModel { MainViewModel(get()) }
}

val detailViewModel = module {
    viewModel { DetailViewModel() }
}