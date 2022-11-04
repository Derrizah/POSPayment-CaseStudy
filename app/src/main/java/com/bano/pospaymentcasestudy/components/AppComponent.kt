package com.bano.pospaymentcasestudy.components

import android.content.Context
import com.bano.pospaymentcasestudy.base.BaseViewModel
import com.bano.pospaymentcasestudy.modules.DatabaseModule
import com.bano.pospaymentcasestudy.modules.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DatabaseModule::class])
interface AppComponent {

    var context: Context

    fun inject(app: BaseViewModel)
}