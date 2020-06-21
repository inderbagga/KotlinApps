package com.inderbagga.foundation.app.modules

import com.inderbagga.foundation.ui.source.RepoListDataSourceFactory
import org.koin.dsl.module

/**
 * Created by Inder Bagga on 20/06/20.
 * Email er[dot]inderbagga[at]gmail[dot]com
 */
val repoListDataSourceFactory = module {
    single { RepoListDataSourceFactory(get()) }
}